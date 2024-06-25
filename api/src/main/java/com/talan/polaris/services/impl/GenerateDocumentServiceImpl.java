package com.talan.polaris.services.impl;

import com.talan.polaris.Application;
import com.talan.polaris.common.EncryptCommonMethod;
import com.talan.polaris.common.QRCodeGenerator;
import com.talan.polaris.common.WordMerge;
import com.talan.polaris.constants.ParamTemplateConstants;
import com.talan.polaris.dto.edmmodelsdto.DocumentDTO;
import com.talan.polaris.entities.DocumentRequestEntity;

import com.talan.polaris.enumerations.*;
import com.talan.polaris.services.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.RoundingMode;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static com.talan.polaris.constants.CommonConstants.*;


@Service
public class GenerateDocumentServiceImpl implements GenerateDocumentService {

    private final DocumentRequestService documentRequestService;
    private final EDMService edmService;
    private final PayRollService payRollService;
    private final RequestTypeService requestTypeService;
    private final SalaryHistoryService salaryHistoryService;

    @Autowired
    public GenerateDocumentServiceImpl(@Lazy DocumentRequestService documentRequestService, EDMService edmService, PayRollService payRollService, SalaryHistoryService salaryHistoryService, RequestTypeService requestTypeService) {
        this.edmService = edmService;
        this.payRollService = payRollService;
        this.requestTypeService = requestTypeService;
        this.salaryHistoryService = salaryHistoryService;
        this.documentRequestService = documentRequestService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateDocumentServiceImpl.class);


    /**
     * generateDocument
     *
     * @param request
     * @throws Exception
     */
    @Override
    public Long generateDocument(DocumentRequestEntity request) throws Exception {
        //get template from EDM
        Long idEdm = request.getType().getIdEDM();
        //with template
        if (idEdm != null) {
            request.setWithoutTemplate(false);
            documentRequestService.create(request);
            //replace variable by data
            HashMap<String, String> variables = replaceDocVariables(request);
            //generate new document
            return this.mappingTemplateData(variables, this.convertBytesToFile(idEdm), request);
            //without template
        } else {
            request.setWithoutTemplate(true);
            documentRequestService.create(request);
            return null;
        }
    }

    /**
     * convert blob to word document
     *
     * @param idEDM
     * @return
     * @throws IOException
     */
    public File convertBytesToFile(Long idEDM) throws IOException {
        byte[] templateBytes = edmService.downloadFileFromEDM(idEDM);
        File file = createFile();
        FileUtils.writeByteArrayToFile(file, templateBytes);
        return file;
    }

    public boolean detectVoyelle(String qualif){
        ArrayList<String> voyelleList = new ArrayList<String>();
        voyelleList.add("a");
        voyelleList.add("e");
        voyelleList.add("i");
        voyelleList.add("o");
        voyelleList.add("u");
        voyelleList.add("y");
        char firstLetter = qualif.toLowerCase().toLowerCase().charAt(0);
        return voyelleList.contains(String.valueOf(firstLetter));
    }
    /**
     * replace variable by values
     *
     * @param request
     * @return
     */
    public HashMap<String, String> replaceDocVariables(DocumentRequestEntity request) {
        HashMap<String, String> variables = new HashMap<>();
        try {

            if (request != null && request.getCollaborator() != null /*&& request.getCollaborator().getCollab() != null*/) {
                //common data
                variables.put(ParamTemplateConstants.DATE, this.localDateFormat());
                variables.put(ParamTemplateConstants.COLLABORATOR, request.getCollaborator().getFirstName() + " " + request.getCollaborator().getLastName());
                variables.put(ParamTemplateConstants.REF, REFERENCE_VAR);
                //signatory
                if (request.getType().getSignatory() != null && request.getType().getSignatory().getFirstName() != null && request.getType().getSignatory().getLastName() != null) {
                    String signatory = request.getType().getSignatory().getFirstName() + " " + request.getType().getSignatory().getLastName();
                    variables.put(ParamTemplateConstants.SINGATAIRE, signatory);
                    variables.put(ParamTemplateConstants.SINGATAIRE_MAJUS, signatory);
                }
                //civility
                if (request.getCollaborator().getCivility() != null) {
                    variables.put(ParamTemplateConstants.CIVILITY, request.getCollaborator().getCivility().getLabel());

                    if (request.getCollaborator().getCivility().getLabel().equals(CivilityEnum.FEMALE.getCivilty())) {
                        variables.put(ParamTemplateConstants.E_FEMININ, FEMININ_E);
                    } else {
                        variables.put(ParamTemplateConstants.E_FEMININ, "");
                    }
                } else {
                    variables.put(ParamTemplateConstants.CIVILITY, "   ");

                }
                //end contract date
                LOGGER.info("Traiter les collaborateurs démissionaires");
                if (request.getCollaborator().getEndContractDate() != null && (request.getCollaborator().getEndContractDate().compareTo(getLocalDate()) == 0 || request.getCollaborator().getEndContractDate().compareTo(getLocalDate()) > 0)) {
                    String collaborateurDemissionnaire = null;
                    if (request.getCollaborator().getCivility() != null) {
                        collaborateurDemissionnaire = request.getCollaborator().getCivility().getLabel() + " " + request.getCollaborator().getFirstName() + " " + request.getCollaborator().getLastName() + " est démissionnaire et s’engage à respecter la clause du préavis et ce jusqu’au " + dateFormat(request.getCollaborator().getEndContractDate()) + '.';
                    } else {
                        collaborateurDemissionnaire = " " + request.getCollaborator().getFirstName() + " " + request.getCollaborator().getLastName() + " est démissionnaire et s’engage à respecter la clause du préavis et ce jusqu’au " + dateFormat(request.getCollaborator().getEndContractDate()) + '.';
                    }
                    variables.put(ParamTemplateConstants.COLLAB_DEMISSIONAIRE, collaborateurDemissionnaire);
                    LOGGER.info("Le collaborateur {} est démissionaire avec une date de sortie {}", request.getCollaborator().getFirstName() + " " + request.getCollaborator().getLastName() + request.getCollaborator().getEndContractDate());

                } else {
                    variables.put(ParamTemplateConstants.COLLAB_DEMISSIONAIRE, "");
                    LOGGER.info("Le collaborateur {} est non démissionaire ", request.getCollaborator().getFirstName() + " " + request.getCollaborator().getLastName());
                }
                //qualification
                variables.put(ParamTemplateConstants.DATE_DEBUT, dateFormat(request.getCollaborator().getEntryDate()));
                if(detectVoyelle(request.getCollaborator().getFunction().getLibelle())) {
                    if (request.getCollaborator().getQualification() != null && request.getCollaborator().getFunction() != null) {
                        variables.put(ParamTemplateConstants.QUALIFICATION, "d'"+request.getCollaborator().getFunction().getLibelle() + " " + request.getCollaborator().getQualification().getSousQualification());
                    } else if (request.getCollaborator().getQualification() != null && request.getCollaborator().getFunction() == null) {
                        variables.put(ParamTemplateConstants.QUALIFICATION, request.getCollaborator().getQualification().getSousQualification());
                    } else if (request.getCollaborator().getQualification() == null && request.getCollaborator().getFunction() != null) {
                        variables.put(ParamTemplateConstants.QUALIFICATION, "d'"+request.getCollaborator().getFunction().getLibelle());
                    } else {
                        variables.put(ParamTemplateConstants.QUALIFICATION, "          ");
                    }
                }
                else if(!detectVoyelle(request.getCollaborator().getFunction().getLibelle())) {
                    if (request.getCollaborator().getQualification() != null && request.getCollaborator().getFunction() != null) {
                        variables.put(ParamTemplateConstants.QUALIFICATION, "de "+request.getCollaborator().getFunction().getLibelle() + " " + request.getCollaborator().getQualification().getSousQualification());
                    } else if (request.getCollaborator().getQualification() != null && request.getCollaborator().getFunction() == null) {
                        variables.put(ParamTemplateConstants.QUALIFICATION, request.getCollaborator().getQualification().getSousQualification());
                    } else if (request.getCollaborator().getQualification() == null && request.getCollaborator().getFunction() != null) {
                        variables.put(ParamTemplateConstants.QUALIFICATION, "de "+request.getCollaborator().getFunction().getLibelle());
                    } else {
                        variables.put(ParamTemplateConstants.QUALIFICATION, "          ");
                    }
                }
                //salary certificate
                if (request.getType().getLabel().equals(RequestTypeEnum.SALARY_CERTIFICATE)) {
                    //annual
                    Double annualSalaryNet = payRollService.annualSalary(request.getCollaborator(), SalaryTypeEnum.NET);
                    Double annualSalaryBase = payRollService.annualSalary(request.getCollaborator(), SalaryTypeEnum.BASE);
                    Double annualBonus = payRollService.annualSalary(request.getCollaborator(), SalaryTypeEnum.BONUS);
                    if (annualSalaryNet != null) {
                        //.0
                        if (annualSalaryNet % 1 == 0) {
                            int convertAnnualSalaryNetToInt = (int) Math.round(annualSalaryNet);
                            variables.put(ParamTemplateConstants.NET_ANNUAL_SALARY_CH, Integer.toString(convertAnnualSalaryNetToInt));

                        } else {
                            variables.put(ParamTemplateConstants.NET_ANNUAL_SALARY_CH, getThreeDecimal(annualSalaryNet).replace(".", ","));
                        }

                    }
                    if (annualSalaryBase != null) {
                        //.0
                        if (annualSalaryBase % 1 == 0) {
                            int convertAnnualSalaryBase = (int) Math.round(annualSalaryBase);
                            variables.put(ParamTemplateConstants.BASE_ANNUAL_SALARY_CH, Integer.toString(convertAnnualSalaryBase));

                        } else {

                            variables.put(ParamTemplateConstants.BASE_ANNUAL_SALARY_CH, getThreeDecimal(annualSalaryBase).replace(".", ","));
                        }
                    }
                    //bonus
                    if (annualBonus != null) {
                        Double zero = 0.0;
                        if (Double.compare(zero, annualBonus) < 0) {
                            if (annualBonus % 1 == 0) {
                                int convertAnnualBonusToInt = (int) Math.round(annualBonus);
                                if (request.getCollaborator().getCivility() != null && request.getCollaborator().getFirstName() != null && request.getCollaborator().getLastName() != null) {
                                    variables.put(ParamTemplateConstants.BONUS_CERTIFICATE, request.getCollaborator().getCivility().getLabel() + " " + request.getCollaborator().getFirstName() + " " + request.getCollaborator().getLastName()
                                            + " a perçu la somme de " + convertAnnualBonusToInt + " DT en brut en tant que bonus pour l’exercice " + LocalDate.now().getYear() + ".");
                                }
                            } else {
                                if (request.getCollaborator().getCivility() != null && request.getCollaborator().getFirstName() != null && request.getCollaborator().getLastName() != null) {
                                    variables.put(ParamTemplateConstants.BONUS_CERTIFICATE, request.getCollaborator().getCivility().getLabel() + " " + request.getCollaborator().getFirstName() + " " + request.getCollaborator().getLastName()
                                            + " a perçu la somme de " + getThreeDecimal(annualBonus).replace(".", ",") + " DT en brut en tant que bonus pour l’exercice " + LocalDate.now().getYear() + ".");
                                }
                            }
                        }
                        if (Double.compare(annualBonus, zero) == 0) {
                            variables.put(ParamTemplateConstants.BONUS_CERTIFICATE, "");
                        }
                    }
                    //mensuel

                    if (salaryHistoryService.getSalaryHistoryByMonth(request.getCollaborator().getId(), payRollService.getDateByType(DateTypeEnum.CURRENT)) != null) {
                        Double currentMonthSalary = EncryptCommonMethod.decryptStringToDouble(salaryHistoryService.getSalaryHistoryByMonth(request.getCollaborator().getId(), payRollService.getDateByType(DateTypeEnum.CURRENT)).getEncryptedNetSalary());
                        if (currentMonthSalary % 1 == 0) {
                            int convertCurrentMonthSalaryToInt = (int) Math.round(currentMonthSalary);
                            variables.put(ParamTemplateConstants.NET_MONTHLY_SALARY_CH, Integer.toString(convertCurrentMonthSalaryToInt));

                        } else {
                            variables.put(ParamTemplateConstants.NET_MONTHLY_SALARY_CH, getThreeDecimal(currentMonthSalary).replace(".", ","));
                        }
                    } else {
                        variables.put(ParamTemplateConstants.NET_MONTHLY_SALARY_CH, Double.toString(ZERO_VALUE));

                    }

                    variables.put(ParamTemplateConstants.ANNEE, Integer.toString(LocalDate.now().getYear()));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return variables;
    }

    /**
     * change local date format
     *
     * @return
     */
    public String localDateFormat() {
        return DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN, Locale.FRENCH).format(LocalDate.now());
    }

    public Date getLocalDate() { //Creating LocalDate instance
        LocalDate now = LocalDate.now();
        //Creating ZonedDateTime instance
        ZonedDateTime zdt = now.atStartOfDay(ZoneId.systemDefault());
        //Creating Instant instance
        Instant instant = zdt.toInstant();
        //Creating Date instance using instant instance.
        Date date = Date.from(instant);
        return date.from(instant);
    }

    /**
     * change date.utils format
     *
     * @param date
     * @return
     */
    public String dateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_PATTERN, Locale.FRENCH);
        return formatter.format(date);
    }

    /**
     * @param variables
     * @param file
     * @throws Exception
     */
    public Long mappingTemplateData(HashMap<String, String> variables, File file, DocumentRequestEntity request) throws Exception {
        //path to save new document
        Long idEdm = null;
        try {
            String dir = Application.class.getResource("/").getFile();
            String generatedFilePath = dir + request.getType().getLabel() + LocalDate.now() + request.getCollaborator().getId() + "-" + generateRandomNumber() + ".docx";
            //mapping data to variables
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(file);
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            VariablePrepare.prepare(wordMLPackage);
            documentPart.variableReplace(variables);
            //create new file
            idEdm = writeDocxToStream(wordMLPackage, generatedFilePath, request);
        } catch (Docx4JException e) {
            LOGGER.error("docx4j Exception" + e.getMessage());
        } catch (NullPointerException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            //delete template file
            Files.delete(Path.of(file.getAbsolutePath()));
        }
        return idEdm;
    }


    /**
     * @param template
     * @param target
     * @param request
     * @throws Docx4JException
     * @throws IOException
     */
    public Long writeDocxToStream(WordprocessingMLPackage template, String target, DocumentRequestEntity request) throws Exception {
        DocumentDTO documentInformations = null;
        File fileGenerated = null;
        try {
            fileGenerated = new File(target);
            template.save(fileGenerated);
            MultipartFile multipartFile = convertFileToMultiPartFile(fileGenerated);
            documentInformations = edmService.uploadFileToEDM(request.getCollaborator().getId().toString(), request.getType().getLabel().toString() + request.getCollaborator().getId().toString() + LocalDate.now() + generateRandomNumber(), request.getCollaborator().getId().toString(), multipartFile);
        } catch (NullPointerException e) {
            e.getMessage();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());

        } finally {
            //delete generated file
            Files.delete(Path.of(fileGenerated.getAbsolutePath()));
        }
        return documentInformations.getId();
    }

    /**
     * @param file
     * @return
     * @throws IOException
     */
    public MultipartFile convertFileToMultiPartFile(File file) throws IOException {
        String mimeType = Files.probeContentType(Path.of(file.getAbsolutePath()));
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(),
                file.getName(), mimeType, IOUtils.toByteArray(input));
        input.close();
        return multipartFile;
    }

    /**
     * @return random number
     */
    public int generateRandomNumber() {
        return new Random().nextInt(5000);
    }

    /**
     * create file in target/classes
     *
     * @return
     */
    public File createFile() {
        String dir = Application.class.getResource("/").getFile();
        String path = dir + "file" + generateRandomNumber() + ".docx";
        return new File(path);
    }

    /**
     * @param number
     * @return
     */
    public String getThreeDecimal(Double number) {
        Double numberRoundOff = Math.round(number * 1000.0) / 1000.0;
        String zeroThreeDecimalPattern = "0.000";
        DecimalFormat df = new DecimalFormat(zeroThreeDecimalPattern);
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(numberRoundOff);

    }

    /**
     * add reference to generated file
     */
    @Override
    public Long addReferenceToDocument(DocumentRequestEntity request) throws Exception {
        File file = this.convertBytesToFile(request.getIdEDM());
        HashMap<String, String> variables = new HashMap<>();
        String reference = request.getReference();
        variables.put(ParamTemplateConstants.REF, reference);
        return this.mappingTemplateData(variables, file, request);
    }

    /**
     * Generate etiquette of a generated request
     */
    @Override
    public Long EtiquetteGenerate(DocumentRequestEntity request) throws Exception {
        //get Etiquette template from EDM
        Long idEdm = this.requestTypeService.getTypeByLabel(RequestTypeEnum.ETIQUETTE_TYPE).getIdEDM();
        //replace variable by data
        try {
            HashMap<String, String> variables = replaceDocVariables(request);
            String civility = request.getCollaborator().getCivility().getAbrev();
            if (civility.equalsIgnoreCase(ABRV_CIVILITY_MR)) {
                variables.put(ParamTemplateConstants.CIVILITY, ABRV_CIVILITY_MR);
            } else {
                variables.put(ParamTemplateConstants.CIVILITY, ABRV_CIVILITY_MME);
            }
               variables.put(ParamTemplateConstants.CODE,request.getId().toString());
            //generate Etiquette
            return this.mappingEtiquetteTemplateData(variables, this.convertBytesToFile(idEdm), request);

        }catch(Exception e){
            LOGGER.error("EtiquetteGenerate error {} ", e.getMessage());
            return -1L;
        }
    }


    /**
     *
     * @param requests
     * @return
     * @throws IOException
     */
    @Override
    public ArrayList<String> convertByteToFile(Collection<DocumentRequestEntity> requests) throws Exception {
        ArrayList<String> files = new ArrayList<>();
        for (DocumentRequestEntity request : requests) {
            if (request.getIdEDM() != null) {
                byte[] templateBytes = edmService.downloadFileFromEDM(request.getIdEDM());
                File fileTemplate = createTemplateFileToSend(request);
                FileUtils.writeByteArrayToFile(fileTemplate, templateBytes);
                files.add(fileTemplate.getAbsolutePath());
            }
        }
        //generate etiquettes
        File etiquette = this.createEtiquetteMergedFile();
        FileOutputStream etiquetteFos = new FileOutputStream(etiquette);
        File etiquettes = this.mergeEtiquette(requests, etiquette, etiquetteFos);
        files.add(etiquettes.getAbsolutePath());
        return files;
    }

    public File createTemplateFileToSend(DocumentRequestEntity request) {
        String dir = Application.class.getResource("/").getFile();
        String path = dir + request.getType().getLabel() + "_" + request.getCollaborator().getFirstName() + request.getCollaborator().getLastName() + generateRandomNumber() + ".docx";
        return new File(path);
    }

    public File createEtiquetteFileToSend(DocumentRequestEntity request) {
        String dir = Application.class.getResource("/").getFile();
        String path = dir + "Etiquette_" + request.getCollaborator().getFirstName() + request.getCollaborator().getLastName() + generateRandomNumber() + ".docx";
        return new File(path);
    }

    /**
     * @param attachmentsPath
     * @throws IOException
     */
    @Override
    public void deleteLocalFile(ArrayList<String> attachmentsPath) {
        for (String filePath : attachmentsPath) {
            Path path = FileSystems.getDefault().getPath(filePath);
            try {
                Files.delete(path);
            } catch (NoSuchFileException x) {
                LOGGER.error("%s: no such" + " file or directory%n", path);
            } catch (IOException x) {
                LOGGER.error(x.getMessage());
            }
        }
    }

    @Override
    public File mergeEtiquette(Collection<DocumentRequestEntity> requestEntities, File etiquette, FileOutputStream etiquetteFos) throws Exception {
        LOGGER.info("Start merge etiquette");
        WordMerge wm = new WordMerge(etiquetteFos);
        for (DocumentRequestEntity request : requestEntities) {
            try {
                File file = this.convertBytesToFile(request.getIdEtiquetteEDM());
                InputStream targetStream = FileUtils.openInputStream(file);
                wm.add(targetStream);
                wm.closeInputFile(targetStream);
                Files.delete(Path.of(file.getAbsolutePath()));
            } catch (Exception exception) {
                LOGGER.info("exception {} ", exception.getMessage());
            }

        }
        wm.doMerge();
        wm.close();
        LOGGER.info("End merge etiquette");

        return etiquette;

    }

    @Override
    public File createEtiquetteMergedFile() {
        LOGGER.info("Start create etiquette file");
        try {
            File file = File.createTempFile("Etiquette", ".Docx");
            LOGGER.info("End create etiquette file");
            return file;
        } catch (IOException e) {
            LOGGER.info("End create etiquette file with exception {}", e.getMessage());

            e.printStackTrace();
            return null;
        }


    }

    public static void addImagesToWordDocument(File imageFile,String generatedFilePath )
            throws IOException, InvalidFormatException {
        LOGGER.info("Start to add images To Word Document");
        FileInputStream is = null ;
        XWPFDocument doc = null;
        FileOutputStream out =null;
        try
        {    is = new FileInputStream(generatedFilePath);
             doc = new XWPFDocument(is);
            XWPFParagraph p = doc.createParagraph();
            p.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r = p.createRun();
            BufferedImage bimg1 = ImageIO.read(imageFile);

            int width1 = bimg1.getWidth();
            int height1 = bimg1.getHeight();

            String imgFile = imageFile.getName();
            int imgFormat = XWPFDocument.PICTURE_TYPE_PNG;
            r.addBreak();
            r.addPicture(new FileInputStream(imageFile), imgFormat, imgFile, Units.toEMU(width1), Units.toEMU(height1));
            out = new FileOutputStream(generatedFilePath);

            doc.write(out);
            LOGGER.info("End to add images To Word Document");

        }
        catch (Exception e){
            LOGGER.info("Exception while add images To Word Document");
        }finally {
            is.close();
            out.close();
            doc.close();
        }



    }

    /**
     * @param variables
     * @param file
     * @throws Exception
     */
    public Long mappingEtiquetteTemplateData(HashMap<String, String> variables, File file, DocumentRequestEntity request) throws Exception {
        //path to save new document
        LOGGER.info("Start mapping etiquette template data");

        Long idEdm = null;
        try {
            String dir = Application.class.getResource("/").getFile();
            String generatedFilePath = dir + request.getType().getLabel() + LocalDate.now() + request.getCollaborator().getId() + "-" + generateRandomNumber() + ".docx";
            //mapping data to variables

            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(file);
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            VariablePrepare.prepare(wordMLPackage);
            documentPart.variableReplace(variables);
            //create new file
             idEdm = writeDocxEtiquetteToStream(wordMLPackage, generatedFilePath, request);

        } catch (Docx4JException e) {
            LOGGER.info("Exception while mapping etiquette template data");
            LOGGER.error("docx4j Exception {} ",e.getMessage());
        } catch (Exception e) {
            LOGGER.info("Exception while mapping etiquette template data");
            LOGGER.error(e.getMessage());

        } finally {
            //delete template file
      Files.delete(Path.of(file.getAbsolutePath()));
            LOGGER.info("End mapping etiquette template data");

        }
        return idEdm;
    }



    public Long  writeDocxEtiquetteToStream(WordprocessingMLPackage template, String target, DocumentRequestEntity request) throws Exception {
        DocumentDTO documentInformations = null;
        File fileGenerated = null;
        LOGGER.info("Start write docx etiquette to stream");
        try {
            fileGenerated = new File(target);
            template.save(fileGenerated);
            File imageQrCode = File.createTempFile(QRCODE_IMAGE_NAME, QRCODE_IMAGE_TYPE);
            String qrCodeText= request.getId().toString();     ;
            QRCodeGenerator.generateQRCodeImage(qrCodeText, QRCODE_IMAGE_WIDTH, QRCODE_IMAGE_HEIGHT, imageQrCode.getPath());
            addImagesToWordDocument(imageQrCode,fileGenerated.getPath());
            MultipartFile multipartFile = convertFileToMultiPartFile(fileGenerated);
            documentInformations = edmService.uploadFileToEDM(request.getCollaborator().getId().toString(), request.getType().getLabel().toString() + request.getCollaborator().getId().toString() + LocalDate.now() + generateRandomNumber(), request.getCollaborator().getId().toString(), multipartFile);
        } catch (NullPointerException e) {
            LOGGER.info("Exception while write docx etiquette to stream");
            e.getMessage();
        } catch (Exception e) {
            LOGGER.info("Exception while write docx etiquette to stream");

            LOGGER.error(e.getMessage());

        } finally {
            //delete generated file
            Files.delete(Path.of(fileGenerated.getAbsolutePath()));
            LOGGER.info("End write docx etiquette to stream");

        }
        return documentInformations.getId();
    }

}
