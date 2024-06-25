package com.talan.polaris.controllers;

import com.talan.polaris.dto.TestResponsesDTO;
import com.talan.polaris.services.ResultTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("result")
public class ResultTestController {
    @Autowired
    private ResultTestService resultTestService;

    @PostMapping("/create")
    public TestResponsesDTO create(@RequestBody TestResponsesDTO resultDto) {
        resultTestService.saveResponses(resultDto);
        return resultDto;
    }
}
