package com.talan.polaris.controllers;

import com.talan.polaris.dto.CandidateDTO;
import com.talan.polaris.dto.ResultTestQuestionDTO;
import com.talan.polaris.dto.TestResponsesDTO;
import com.talan.polaris.entities.TestEntity;
import com.talan.polaris.mapper.candidateMapper;
import com.talan.polaris.services.TestService;
import com.talan.polaris.services.candidateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("test")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class testController {

    @Autowired
   private TestService testService;

    @Autowired
    private candidateService candidateService;


    @GetMapping("/findtest/{id}")
    public ResponseEntity<TestEntity> findById(@PathVariable(name = "id") Long id) {
        Optional<TestEntity> test = testService.findById(id);
        if (test.isPresent()) {
            return ResponseEntity.ok(test.get());
        } else {
            return ResponseEntity.ok(null);
        }
    }
    @PostMapping("/RespondQuestion")
    public TestResponsesDTO respond (@RequestBody TestResponsesDTO resulDto){
        return null;
    }

    @GetMapping("/getCandidate/{id}")
    public CandidateDTO getCandidateByTestId (@PathVariable Long id ){
        return candidateMapper.convertCandidateEntityToDTO(testService.getCandidateByTestId(id), new ModelMapper());
    }

    @GetMapping("/testResult/{id}")
    public ResponseEntity<List<ResultTestQuestionDTO>> findTestResult(@PathVariable(name = "id") Long test) {
        return ResponseEntity.ok(testService.getResultDetail(test));
    }
    @GetMapping("/testByCandidate/{id}")
    public ResponseEntity<TestEntity> findTestByCandidate(@PathVariable(name = "id") Long idCandidate){
        return ResponseEntity.ok(testService.getTestByCandidate(idCandidate));
    }

}
