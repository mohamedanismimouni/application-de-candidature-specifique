package com.talan.polaris.services.impl;

import com.talan.polaris.dto.byblosmodelsdto.CollabByblosDTO;
import com.talan.polaris.dto.byblosmodelsdto.LoginByblosDTO;
import com.talan.polaris.dto.byblosmodelsdto.SalaryHistByblosDTO;
import com.talan.polaris.entities.*;
import com.talan.polaris.enumerations.AccountStatusEnum;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.repositories.CollaboratorRepository;
import com.talan.polaris.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static com.talan.polaris.constants.CommonConstants.*;

import static com.talan.polaris.constants.ConfigurationConstants.*;
import static com.talan.polaris.constants.ConfigurationConstants.BYBLOS_USER_PWD;

@Service
public class CollaboratorAPIByblosServiceImpl implements CollaboratorAPIByblosService {


    private static final Logger LOGGER = LoggerFactory.getLogger(CollaboratorAPIByblosServiceImpl.class);

    private String sessionId;

    @Autowired
    CareerPositionService careerPositionService;
    @Autowired
    PasswordEncoder passwordEncoder;

   /* @Autowired
    UserService userService;*/

    @Autowired
    TeamService teamService;
    @Autowired
    CivilityService civilityService;
    @Autowired
    QualificationService qualificationService;
    @Autowired
    ProfileService profileService;
    @Autowired
    FunctionService functionService;

    @Autowired
    ProfileTypeService profileTypeService;

    @Value("${" + BYBLOS_BASE_PATH + "}")
    private String baseByblosPath;

    @Value("${" + BYBLOS_LOGIN_PATH + "}")
    private String byblosLoginPath;

    @Value("${" + BYBLOS_GET_COLLAB_PATH + "}")
    private String byblosGetCollabPath;

    @Value("${" + BYBLOS_USER_LOG_IN + "}")
    private String byblosUserLogIn;

    @Value("${" + BYBLOS_USER_PWD + "}")
    private String byblosUserPwd;


    private RestTemplate restTemplate = new RestTemplate();
    private final CollaboratorRepository collabRepository;
    private final CollaboratorService collaboratorService;
    private static final String DEFAULT_SORT_FIELD_NAME = "createdAt";

    @Autowired
    public CollaboratorAPIByblosServiceImpl(CollaboratorRepository collabRepository,
                                            ProfileTypeService profileTypeService,
                                            CollaboratorService collaboratorService) {

        this.collabRepository = collabRepository;
        this.profileTypeService = profileTypeService;
        this.collaboratorService = collaboratorService;
    }

    @Override
    public CollaboratorEntity findCollabByMatriculeFirstLastName(String matricule, String firstName, String lastName) {
        return this.collabRepository.findCollabByMatriculeFirstLastName(matricule,firstName,lastName);
    }


   @Override
    public Collection<CollaboratorEntity> findAllCollabWithUser() {
        return this.collabRepository.findByIdNotNull();
    }


    @Override
    public Collection<CollaboratorEntity> synchronizeCollaborators() {
        loadCollaboratorsFromByblos();
        return this.collabRepository.findAll(Sort.by(Sort.Direction.DESC, DEFAULT_SORT_FIELD_NAME));
    }

    public void loadCollaboratorsFromByblos() {

        try {

            //Generate a session id
            sessionId = generateSessionId();
            //get list of byblos collaborators
            List<CollabByblosDTO> collabByblos = this.getCollaboratorsFromByblos(sessionId);
            LOGGER.info("New collaborator from byblos : " + collabByblos);
            this.synchronizeCollabStatus(collabByblos);
            //save new collaborators
            this.saveNewCollaborators(collabByblos);

        } catch (Exception e) {
            LOGGER.info(" can't load collaborator list");
        }
    }


   /**
     * Connect with user and generate session id
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String generateSessionId() {
        if (sessionId != null) {
            return sessionId;
        } else {
            try {
                String url = byblosLoginPath;
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);

                LoginByblosDTO loginDTO = new LoginByblosDTO(byblosUserLogIn, byblosUserPwd);

                HttpEntity<String> entity = new HttpEntity(loginDTO, httpHeaders);
                final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
                ResponseEntity<String> responseEntity = restTemplate.exchange(builder.build().encode().toUri(),
                        HttpMethod.POST, entity, String.class);

                if (responseEntity.getHeaders().containsKey(HttpHeaders.SET_COOKIE)) {
                    sessionId = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE).split(";")[0];
                    return sessionId;
                } else {

                    return null;
                }
            } catch (RestClientException e) {
                return null;
            }
        }
    }

    /**
     * get list of collaborators from byblos
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<CollabByblosDTO> getCollaboratorsFromByblos(String sessionId) {
        if (sessionId != null) {
            String url = byblosGetCollabPath;
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.COOKIE, sessionId);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("actifOnly", "Y")
                    .queryParam("idSoc", 3)
                    .build();

            HttpEntity<CollabByblosDTO> entity = new HttpEntity(httpHeaders);
            try {
                ResponseEntity<List<CollabByblosDTO>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<CollabByblosDTO>>() {
                });
                return responseEntity.getBody();

            } catch (HttpClientErrorException exception) {
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }

    /**
     * save new users
     *
     * @param collabByblosDTO
     */
    /*public void saveUser(CollabByblosDTO collabByblosDTO) {
        try {
            //check if user has an email
            if (collabByblosDTO.getMailPro() != null) {
                this.collaboratorService.findUserByEmail(collabByblosDTO.getMailPro());
                LOGGER.info("the user " + collabByblosDTO.getFirstName() + " " + collabByblosDTO.getLastName() + " already exist");

            }
        }
        //check is a new user
        catch (AccountNotFoundException e) {

            try {
                LOGGER.info("Start add user " + collabByblosDTO.getFirstName() + " " + collabByblosDTO.getLastName());

                if (collabByblosDTO.getMatricule() != null && !collabByblosDTO.getMatricule().isEmpty()) {
                    CollaboratorEntity collabEntity = this.collaboratorService.findById(collabByblosDTO.getId());

                        UserEntity collaboratorEntity = new UserEntity();
                        collaboratorEntity.setPassedOnboardingProcess(true);
                        Date input = collabByblosDTO.getHiringDate();
                        LocalDate hiringDate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        collaboratorEntity.setRecruitedAt(hiringDate);

                    collaboratorEntity.setCollab(collabEntity);

                    collaboratorEntity.setFirstName(collabByblosDTO.getFirstName());
                    collaboratorEntity.setLastName(collabByblosDTO.getLastName());

                    collaboratorEntity.setMemberOf(getCollabTeam());


                    collaboratorEntity.setAccountStatus(AccountStatusEnum.ACTIVE);

                       //collaboratorEntity.setProfileType(ProfileTypeEnum.COLLABORATOR);
                        Collection<ProfileTypeEntity> profileTypeEntityList = new ArrayList<>();
                        profileTypeEntityList.add(this.profileTypeService.findProfileTypeByLabel(ProfileTypeEnum.COLLABORATOR));
                        collaboratorEntity.setProfileTypeEntity(profileTypeEntityList);

                        collaboratorEntity.setEmail(collabByblosDTO.getMailPro());

                        collaboratorEntity.setPassword(this.passwordEncoder.encode(INITIALIZE_USER_PWD));

                        try {
                            UserEntity userEntity = this.userService.createUser(collaboratorEntity);
                            LOGGER.info("End add user "+collaboratorEntity.getFirstName()+" "+collaboratorEntity.getLastName());
                            userInitialize(userEntity ,hiringDate);
                        }catch (Exception exception)
                        {
                            LOGGER.info("Exception while add user "+collaboratorEntity.getFirstName()+" "+collaboratorEntity.getLastName());
                        }


                } else {
                    LOGGER.info("the user {} {} hasn't a matricule", collabByblosDTO.getFirstName(), collabByblosDTO.getLastName());
                }
            } catch (Exception exception) {
                LOGGER.error("Exception when adding the user {} {}", collabByblosDTO.getFirstName(), collabByblosDTO.getLastName());
            }

        }
    }*/


    /**
     * save and update Collaborators
     *
     * @param collabByblosDTO
     */
    public void saveNewCollaborators(List<CollabByblosDTO> collabByblosDTO) {
        for (var collabByblos : collabByblosDTO) {
            try {
                //check if collab has an email
                if (collabByblos.getMailPro() != null) {
                    CollaboratorEntity existCollaborator = this.collaboratorService.findCollabByIdByblos(collabByblos.getId());

                    //check if new collaborator
                    if (existCollaborator == null) {
                        LOGGER.info("Start add collab " + collabByblos.getFirstName() + " " + collabByblos.getLastName());

                        saveCollaborator(collabByblos);
                        LOGGER.info("end add collab " + collabByblos.getFirstName() + " " + collabByblos.getLastName());

                    }
                    //if the collaborator already exist in database
                    else {
                        LOGGER.info("Start Update collab " + collabByblos.getFirstName() + " " + collabByblos.getLastName());
                        //end contract date update
                        updateEndContractDate(existCollaborator, collabByblos);
                        //qualification update
                        updateQualification(existCollaborator, collabByblos);
                        LOGGER.info("And Update collab " + collabByblos.getFirstName() + " " + collabByblos.getLastName());

                    }
                }

            } catch (Exception exception) {
                LOGGER.error("Exception when adding the collaborator {} {}", collabByblos.getFirstName(), collabByblos.getLastName());

            }

        }
    }

    /**
     * update end contract date
     *
     * @param existCollaborator
     * @param collabByblos
     */
    void updateEndContractDate(CollaboratorEntity existCollaborator, CollabByblosDTO collabByblos) {
        LOGGER.info(" start=>update end Contract Date of  {} {} ", existCollaborator.getFirstName(), existCollaborator.getLastName());
        //if end contract date not null
        if (collabByblos.getEndContractDate() != null) {
            LOGGER.info(" End  contract Date from byblos not null");
            existCollaborator.setEndContractDate(collabByblos.getEndContractDate());
        }
        //if end contract date is null
        else {
            LOGGER.info(" End contract Date from byblos with null value");
            existCollaborator.setEndContractDate(null);
        }
        try {
            this.collaboratorService.update(existCollaborator);
            LOGGER.info(" End=>update end contract Date {} {} ", existCollaborator.getFirstName(), existCollaborator.getLastName());

        } catch (Exception exception) {
            LOGGER.info(" End=>update end Contract date user with exception {}  ", exception.getMessage());

        }
    }


    void updateQualification(CollaboratorEntity existCollaborator, CollabByblosDTO collabByblos) {
        LOGGER.info(" start update qualification user {} {} ", existCollaborator.getFirstName(), existCollaborator.getLastName());

        //if collaborator's qualification is updated
        QualificationEntity oldQualification = existCollaborator.getQualification();
        if (collabByblos.getSalaryHist().length > 0) {
            QualificationEntity newQualification = this.getCollabQualification(collabByblos);
            if ((newQualification != oldQualification) || (oldQualification == null)) {
                existCollaborator.setQualification(newQualification);
                try {
                    this.collaboratorService.update(existCollaborator);
                    LOGGER.info(" End update qualification user {} {} ", existCollaborator.getFirstName(), existCollaborator.getLastName());

                } catch (Exception exception) {
                    LOGGER.info(" End update qualification user {} {} with exception, ", existCollaborator.getFirstName(), existCollaborator.getLastName());
                    LOGGER.info(" End update qualification user with exception {}  ", exception.getMessage());

                }

            }
        }

    }

    QualificationEntity getCollabQualification(CollabByblosDTO collabByblosDTO) {
        SalaryHistByblosDTO[] SalaryHistCollab = collabByblosDTO.getSalaryHist();
        Optional<SalaryHistByblosDTO> salaryHistDTO = Arrays.stream(SalaryHistCollab).max((o1, o2) -> o1.getBeginDate().compareTo(o2.getBeginDate()));

        String qualification = salaryHistDTO.get().getQualification().trim();
        String sousQualification = salaryHistDTO.get().getSousQualification().trim();

        if (salaryHistDTO.get().getSousQualification().isBlank()) {
            return this.qualificationService.findIdByLabel(qualification);
        } else {
            return this.qualificationService.findIdByLabelAndSousQualification(qualification, sousQualification);
        }

    }


    CivilityEntity getCollabCivility(CollabByblosDTO collabByblosDTO) {
        String byblosCivily;
        if (collabByblosDTO.getCivility().equalsIgnoreCase(ABRV_CIVILITY_MR)) {
            byblosCivily = ABRV_CIVILITY_MR;
        } else {
            byblosCivily = ABRV_CIVILITY_MME;
        }
        return this.civilityService.findIdByLabel(byblosCivily);
    }

    TeamEntity getCollabTeam() {
        return teamService.findTeamByName(INITIALIZE_TEAM);
    }

    void userInitialize ( CollaboratorEntity userEntity , LocalDate hiringDate )
    {
        LOGGER.info(" start Initialize user {} {} ",userEntity.getFirstName(),userEntity.getLastName());

        try {

            CareerPositionEntity careerPositionEntity = new CareerPositionEntity();

            careerPositionEntity.setCollaborator(userEntity);
            ProfileEntity profileEntity = this.profileService.findProfileByLabel(INITIALIZE_PROFILE);
            careerPositionEntity.setProfile(profileEntity);
            careerPositionEntity.setStatus(CareerPositionStatusEnum.CURRENT);
            careerPositionEntity.setStartedAt(hiringDate);
            careerPositionEntity.setSupervisor(this.collaboratorService.findUserByEmail(INITIALIZE_SUPERVISOR_EMAIL));
            this.careerPositionService.createCareerPosition(careerPositionEntity);
            LOGGER.info(" End Initialize user {} {} ", userEntity.getFirstName(), userEntity.getLastName());

        } catch (Exception exception) {
            LOGGER.info(" can't initialize user : {} with exception {}", userEntity, exception.getMessage());
        }
    }

    void synchronizeCollabStatus(List<CollabByblosDTO> collabByblosDTO) {
        try {
            Collection<CollaboratorEntity> collabEntities = this.collabRepository.findAll();

            for (var collaborator : collabEntities) {
                LOGGER.info("check colaborator for synchronize {} {}", collaborator.getFirstName(), collaborator.getLastName());

                CollabByblosDTO collabByblos = collabByblosDTO.stream()
                        .filter(x -> collaborator.getIdByblos().equals(x.getId()))
                        .findAny()
                        .orElse(null);

                if (collabByblos == null) {
                    LOGGER.info("collaborator {} {} is inactif in byblos", collaborator.getFirstName(), collaborator.getLastName());
                    collaborator.setAccountStatus(AccountStatusEnum.SUSPENDED);
                    this.collabRepository.save(collaborator);
                } else {
                    LOGGER.info("collaborator {} {} was inactif in byblos", collaborator.getFirstName(), collaborator.getLastName());

                    if (collaborator.getAccountStatus().equals(AccountStatusEnum.SUSPENDED)) {

                        LOGGER.info("collaborator {} {} was inactif in byblos", collaborator.getFirstName(), collaborator.getLastName());
                        collaborator.setAccountStatus(AccountStatusEnum.ACTIVE);
                        this.collabRepository.save(collaborator);
                    }
                }
            }
        } catch (Exception exception) {
            LOGGER.info("error while Synchronize CollabList With Byblos actif / inactif {}", exception.getMessage());
        }
    }


    //save new collaborattors from byblos
    void saveCollaborator(CollabByblosDTO collabByblos) {
        if (collabByblos.getMatricule() != null && !collabByblos.getMatricule().isEmpty()) {
            CollaboratorEntity collabEntity = new CollaboratorEntity();
            collabEntity.setEntryDate(collabByblos.getHiringDate());
            collabEntity.setFirstName(collabByblos.getFirstName());
            collabEntity.setLastName(collabByblos.getLastName());
            //end contract Date
            if (collabByblos.getEndContractDate() != null) {
                collabEntity.setEndContractDate(collabByblos.getEndContractDate());
            }

            if (collabByblos.getMatricule() != null) {
                collabEntity.setMatricule(collabByblos.getMatricule().trim());
            }
            collabEntity.setIdByblos(collabByblos.getId());

            //Civility
            collabEntity.setCivility(this.getCollabCivility(collabByblos));

            //Qualification
            if (collabByblos.getSalaryHist().length > 0)
                collabEntity.setQualification(this.getCollabQualification(collabByblos));

            //user
            collabEntity.setPassedOnboardingProcess(true);
            Date input = collabByblos.getHiringDate();
            LocalDate hiringDate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            collabEntity.setRecruitedAt(hiringDate);
            collabEntity.setFirstName(collabByblos.getFirstName());
            collabEntity.setLastName(collabByblos.getLastName());
            collabEntity.setMemberOf(getCollabTeam());
            collabEntity.setAccountStatus(AccountStatusEnum.ACTIVE);
            collabEntity.setEmail(collabByblos.getMailPro());

            try {
                this.collaboratorService.createUser(collabEntity);
                //this.saveUser(collabByblos);
            } catch (Exception exception) {
                LOGGER.info("end add with exception for collab " + collabByblos.getFirstName() + " " + collabByblos.getLastName());
                LOGGER.info("end add with exception for collab msg :" + exception.getMessage());

            }


        } else {
            LOGGER.info("Collaborator {} {} can't be added with null or empty matricule", collabByblos.getFirstName(), collabByblos.getLastName());
        }
    }
}
