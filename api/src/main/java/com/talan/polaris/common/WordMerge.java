package com.talan.polaris.common;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class WordMerge {
    private final OutputStream result;
    private final List<InputStream> inputs;
    private XWPFDocument first;
    private static final Logger LOGGER = LoggerFactory.getLogger(WordMerge.class);
    public WordMerge(OutputStream result) {
        this.result = result;
        inputs = new ArrayList<>();
    }

    public void add(InputStream stream) {
        try {


            OPCPackage srcPackage = OPCPackage.open(stream);
            inputs.add(stream);
            XWPFDocument src1Document = new XWPFDocument(srcPackage);
            src1Document.createParagraph().createRun().addBreak(BreakType.PAGE);

            if (inputs.size() == 1) {
                first = src1Document;
            } else {
                CTBody srcBody = src1Document.getDocument().getBody();
                first.getDocument().addNewBody().set(srcBody);
            }

        }

        catch(Exception  exception)
        {
            LOGGER.info("Exception while adding files");
        }

    }

    public void doMerge() {
        try{

            first.write(result);

        }catch (Exception exception)
        {
            LOGGER.info("Exception while do merge");
        }
    }

    public void closeInputFile(InputStream input ) {
        try{
                            input.close();

        }catch(Exception exception)
        {
            LOGGER.info("Exception while closing files");
        }

    }
    public void close() {
        try{

            result.flush();
            result.close();
            for (InputStream input : inputs) {
                input.close();
            }
        }catch(Exception exception)
        {
            LOGGER.info("Exception while closing files");
        }

    }


}
