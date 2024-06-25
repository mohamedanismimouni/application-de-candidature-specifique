package com.talan.polaris.controllers;
import com.talan.polaris.services.PayRollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
/**
 * A controller defining PayRoll endpoints.
 * @author Imen Mechergui
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/payRoll")
public class PayRollController {
@Autowired
    PayRollService payRollService;

    /**
     * upload Excel File(PayRoll)
     * @param file
     */
    @PostMapping(path = "/uploadPayroll")
    public void uploadPayRollFile(@RequestParam("file") MultipartFile file , @RequestParam("payRollDate") Date payRollDate ) {
        payRollService.processExcelPayrollFile(file,payRollDate);    }

    /**
     * upload reminder File
     * @param file
     * @param reminderDate
     * @throws IOException
     */
    @PostMapping(path = "/uploadReminder")
    public void uploadReminderFile(@RequestParam("file") MultipartFile file , @RequestParam("reminderDate") Date reminderDate ) throws IOException {
        payRollService.processExcelReminderFile(file,reminderDate);
    }

}


