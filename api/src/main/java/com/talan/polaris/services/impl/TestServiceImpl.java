//package com.talan.polaris.services.impl;
//
//import com.talan.polaris.dto.*;
//import com.talan.polaris.entities.*;
//import com.talan.polaris.mapper.TestMapper;
//import com.talan.polaris.mapper.candidateMapper;
//import com.talan.polaris.repositories.*;
//import com.talan.polaris.services.MailSending;
////import com.talan.polaris.services.MailService;
//import com.talan.polaris.services.TestService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import javax.mail.MessagingException;
//import java.io.IOException;
//import java.util.*;
//
//
//@Service
//public class TestServiceImpl implements TestService {
//
//
//    @Autowired
//    ResponseTestRepository respRepository;
//
//    @Autowired
//    QuestionTestRepository questRepository;
//
//    @Autowired
//    TestRepo testRepository;
//
//    private TestMapper testMapper;
//
//
//    @Autowired
//    private TestQuestionRepo testQuestionRepository;
//
//
////    @Autowired
////    private MailService mailService;
//
//    @Autowired
//    private MailSending mailSending;
//
//    @Autowired
//    private candidateRepository candidateRepository;
//
//    @Autowired
//    private ResponseTestRepository responseTestRepository;
//
//    @Autowired
//    private ResultTestRepository resultTestRepository;
//
//
//    @Override
//    @Scheduled(cron = "0 * * * * *")
//    public void updateExpiration() {
//        testRepository.findBySendAndDone(true, false).forEach(test -> {
//            if (new Date().after(test.getExpirationDate())) {
//                test.setExpired(true);
//                testRepository.save(test);
//            }
//        });
//    }
//
//    @Override
//    public Optional<TestEntity> findById(Long id) {
//
//        return testRepository.findById(id);
//    }
//
//    @Override
//    public List<TestDTO> findAll() {
//        return testMapper.toTestDtos(testRepository.findAll());
//    }
//
//    @Override
//    public List<TestQuestionEntity> findByTestId(Long id) {
//        Optional<TestEntity> test = testRepository.findById(id);
//        if(test.isPresent()){
//            return testQuestionRepository.findByTest(test.get());
//        }else{
//            return new ArrayList<>();
//        }
//    }
//
//
//    @Override
//    public boolean sendTest (HashSet<String> technologies , Long idCandidate ) {
//        TestEntity test = getTestByCandidate(idCandidate);
//        Optional<CandidateEntity> candidate = candidateRepository.findById(idCandidate);
//        test.setCandidate(candidate.get());
//        Calendar cal = Calendar.getInstance();
//        Date date = cal.getTime();
//        test.setCreationDate(date);
//        cal.add(Calendar.DAY_OF_WEEK, 7);
//        Date nextDate = cal.getTime();
//        test.setExpirationDate(nextDate);
//        test.setExpired(false);
//        test.setSend(true);
//        test.setTotalScore((double) 0);
//        test.setTestDuration((long) (technologies.size()*210));
//        test.setDone(false);
//        TestEntity tes = testRepository.save(test);
//        for (String technology : technologies) {
//            TestQuestionKeysEntity keys = new TestQuestionKeysEntity();
//            TestQuestionEntity testQuestionFacile = new TestQuestionEntity();
//            List<QuestionTestEntity> questionsFaciles = questRepository.getQuestionsByDifficultyAndTechnology("facile", technology);
//            QuestionTestEntity questionFacile = questionsFaciles.get((int) Math.random() * questionsFaciles.size());
//            testQuestionFacile.setQuestion(questionFacile);
//            keys.setTestid(tes.getTestid());
//            keys.setQuestionid(questionFacile.getQuestionid());
//            testQuestionFacile.setId(keys);
//            testQuestionFacile.setTest(tes);
//            testQuestionRepository.save(testQuestionFacile);
//            TestQuestionEntity testQuestionMoyen = new TestQuestionEntity();
//            List<QuestionTestEntity> questionsMoyens = questRepository.getQuestionsByDifficultyAndTechnology("moyenne", technology);
//            QuestionTestEntity questionMoyen = questionsMoyens.get((int) Math.random() * questionsMoyens.size());
//            TestQuestionKeysEntity keys1 = new TestQuestionKeysEntity();
//            keys1.setTestid(tes.getTestid());
//            keys1.setQuestionid(questionMoyen.getQuestionid());
//            testQuestionMoyen.setId(keys1);
//            testQuestionMoyen.setQuestion(questionMoyen);
//            testQuestionMoyen.setTest(tes);
//            testQuestionRepository.save(testQuestionMoyen);
//            TestQuestionEntity testQuestionDifficile = new TestQuestionEntity();
//            List<QuestionTestEntity> questionsDifficiles = questRepository.getQuestionsByDifficultyAndTechnology("difficile", technology);
//            QuestionTestEntity questionDifficile = questionsDifficiles.get((int) Math.random() * questionsDifficiles.size());
//            TestQuestionKeysEntity keys2 = new TestQuestionKeysEntity();
//            keys2.setQuestionid(questionDifficile.getQuestionid());
//            keys2.setTestid(tes.getTestid());
//            testQuestionDifficile.setId(keys2);
//            testQuestionDifficile.setQuestion(questionDifficile);
//            testQuestionDifficile.setTest(tes);
//            testQuestionRepository.save(testQuestionDifficile);
//        }
//            return true;
//    }
//
//    @Override
//    public List<ResultTestQuestionDTO> getResultDetail(Long idtest) {
//        List<ResultTestQuestionDTO> resultTestQuestionDtos = new ArrayList<>();
//        TestEntity t = testRepository.findById(idtest).get();
//        List<TestQuestionEntity> testQuestionList = testQuestionRepository.findByTest(t);
//        testQuestionList.forEach(tq -> {
//            ResultTestQuestionDTO rtqdto = new ResultTestQuestionDTO();
//            rtqdto.setStatement(tq.getQuestion().getStatement());
//            rtqdto.setTechnologyname(tq.getQuestion().getPrmTechnology().getTechnologyName());
//            rtqdto.setResult(tq.getResult() == 1);
//            rtqdto.setCode(tq.getQuestion().getCode());
//            rtqdto.setScoreTotal(tq.getTest().getTotalScore());
//            rtqdto.setCandidateName(tq.getTest().getCandidate().getFirstName() + tq.getTest().getCandidate().getLastName());
//            rtqdto.setCandidateEmail(tq.getTest().getCandidate().getEmail());
//            rtqdto.setCandidatePhone(String.valueOf(tq.getTest().getCandidate().getPhoneNumber()));
//            List<ResultResponseDTO> resultResponseDtos = new ArrayList<>();
//            List<ResponseTestEntity> responses = responseTestRepository.findByQuestion(tq.getQuestion());
//            responses.forEach(rs ->{
//                ResultResponseDTO rsdto = new ResultResponseDTO();
//                rsdto.setCorrect(rs.getCorrect());
//                rsdto.setValue(rs.getValue());
//                rsdto.setChecked(resultTestRepository.findByTestAndResponse(t,rs).isPresent());
//                resultResponseDtos.add(rsdto);
//            });
//            rtqdto.setResultResponseDtoList(resultResponseDtos);
//            resultTestQuestionDtos.add(rtqdto);
//        });
//        return resultTestQuestionDtos;
//    }
//
//    @Override
//    public CandidateEntity getCandidateByTestId(Long id) {
//        TestEntity test = testRepository.findById(id).get();
//        return test.getCandidate();
//    }
//
//    @Override
//    public TestEntity getTestByCandidate(Long idCandidate) {
//        Optional<CandidateEntity> candidate = candidateRepository.findById(idCandidate);
//        if(candidate.isPresent()){
//            List<TestEntity> tests = testRepository.findByCandidate(candidate.get());
//            return tests.get(0);
//        }else {
//            return null;
//        }
//    }
//}
