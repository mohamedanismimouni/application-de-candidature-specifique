package com.talan.polaris.controllers;

import com.talan.polaris.entities.PrmTechnologyEntity;
import com.talan.polaris.entities.TestQuestionEntity;
import com.talan.polaris.services.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/testQuestion")
public class TestQuestionController {
    @Autowired
    private TestQuestionService testQuestionService;

    @GetMapping ("/find/{testId}")
    public ResponseEntity<List<TestQuestionEntity>> findByTest(@PathVariable("testId")Long id){
        return ResponseEntity.ok(testQuestionService.findByTest(id));
    }
    @GetMapping("/getAllTechnologies/{testId}")
    public ResponseEntity<HashSet<PrmTechnologyEntity>> getAllTechnologies(@PathVariable("testId")Long id){
        return ResponseEntity.ok(testQuestionService.findAllTechnologiesByTest(id));
    }
}
