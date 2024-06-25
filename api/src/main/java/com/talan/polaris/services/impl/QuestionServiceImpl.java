package com.talan.polaris.services.impl;

import com.talan.polaris.entities.ChoiceEntity;
import com.talan.polaris.entities.ChoiceQuestionEntity;
import com.talan.polaris.entities.QuestionEntity;
import com.talan.polaris.entities.QuizChoiceEntity;
import com.talan.polaris.enumerations.SurveyTypeEnum;
import com.talan.polaris.repositories.QuestionRepository;
import com.talan.polaris.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_QUESTION_CHOICE_SUPPORT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_QUESTION_MIN_CHOICES;
/**
 * An implementation of {@link QuestionService}, containing business methods
 * implementations specific to {@link QuestionEntity}, and may override some
 * of the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class QuestionServiceImpl
        extends GenericServiceImpl<QuestionEntity>
        implements QuestionService {

    private final QuestionRepository questionRepository;
    private final Validator validator;

    @Autowired
    public QuestionServiceImpl(
            QuestionRepository repository,
            Validator validator) {

        super(repository);
        this.questionRepository = repository;
        this.validator = validator;


    }

    @Override
    public Collection<QuestionEntity> findQuestionsBySurveyTypeAndEnabledStatus(
            SurveyTypeEnum surveyType,
            Optional<Boolean> enabled) {

        List<QuestionEntity> questions = new ArrayList<>(
                this.questionRepository.findQuestionsBySurveyType(surveyType));

        if (enabled.isPresent())
            questions = questions.stream()
                    .filter(question -> question.isEnabled() == enabled.get())
                    .collect(Collectors.toList());

        questions.forEach(question -> {

            if (question instanceof ChoiceQuestionEntity) {

                ChoiceQuestionEntity choiceQuestion = (ChoiceQuestionEntity) question;
                List<ChoiceEntity> choices = new ArrayList<>(choiceQuestion.getChoices());

                if (enabled.isPresent())
                    choices = choices.stream()
                            .filter(choice -> choice.isEnabled() == enabled.get())
                            .collect(Collectors.toList());

                Collections.sort(choices);
                choiceQuestion.setChoices(choices);

            }

        });

        return questions;

    }

    @Override
    public Collection<QuizChoiceEntity> findQuizChoicesByValidStatus(
            String questionId,
            boolean valid) {

        return this.questionRepository.findQuizChoicesByValidStatus(questionId, valid);

    }

    @Override
    public QuestionEntity createQuestion(QuestionEntity questionEntity) {
        questionEntity.setEnabled(true);
        questionEntity.setPosition(
                this.questionRepository.countQuestionsWithSurveyTypeAndEnabledStatus(
                        questionEntity.getSurveyType(),
                        questionEntity.isEnabled()) + 1);

        if (questionEntity instanceof ChoiceQuestionEntity) {
            ChoiceQuestionEntity choiceQuestionEntity = (ChoiceQuestionEntity) questionEntity;
            List<ChoiceEntity> choices = new ArrayList<>(choiceQuestionEntity.getChoices());
            for (int i = 0; i < choices.size() ; i++) {
                choices.get(i).setId(UUID.randomUUID().toString());
                choices.get(i).setPosition(i + 1);
                choices.get(i).setEnabled(true);
                choices.get(i).setQuestion(choiceQuestionEntity);
            }
        }

        return create(questionEntity);
    }

    @Override
    @Transactional
    public ChoiceEntity createChoice(String questionId, ChoiceEntity choiceEntity) {

        ChoiceQuestionEntity question;

        try {
            question = (ChoiceQuestionEntity) findById(questionId);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(ERROR_BAD_REQUEST_QUESTION_CHOICE_SUPPORT, e);
        }

        choiceEntity.setId(UUID.randomUUID().toString());
        choiceEntity.setPosition(
                question.getChoices()
                        .stream()
                        .filter(choice -> choice.isEnabled())
                        .collect(Collectors.toList())
                        .size() + 1);
        choiceEntity.setEnabled(true);
        choiceEntity.setQuestion(question);

        question.getChoices().add(choiceEntity);

        Set<ConstraintViolation<ChoiceQuestionEntity>> violations = this.validator.validate(question);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

        return choiceEntity;

    }

    @Override
    @Transactional
    public void deleteQuestion(String questionId) {

        QuestionEntity question = findById(questionId);

        if (question.isEnabled())
            this.questionRepository.findQuestionsBySurveyTypeWithPositionGreaterThan(
                    question.getSurveyType(),
                    question.getPosition()).forEach(
                    nextQuestion -> nextQuestion.setPosition(nextQuestion.getPosition() - 1));

        deleteById(questionId);

    }

    @Override
    @Transactional
    public void deleteChoice(String questionId, String choiceId) {

        ChoiceQuestionEntity question;

        try {
            question = (ChoiceQuestionEntity) findById(questionId);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(ERROR_BAD_REQUEST_QUESTION_CHOICE_SUPPORT, e);
        }

        ChoiceEntity choice = question.getChoices()
                .stream()
                .filter(c -> c.getId().equals(choiceId))
                .findFirst()
                .orElseThrow(/*() -> new ResourceNotFoundException(choiceId)*/);

        if (choice.isEnabled()) {

            final int enabledChoicesCount = question.getChoices()
                    .stream()
                    .filter(c -> c.isEnabled())
                    .collect(Collectors.toList())
                    .size();

            if (enabledChoicesCount < 3)
                throw new IllegalArgumentException(ERROR_BAD_REQUEST_QUESTION_MIN_CHOICES);

            question.getChoices().forEach(c -> {
                if (c.getPosition() > choice.getPosition())
                    c.setPosition(c.getPosition() - 1);
            });

        }

        question.getChoices().remove(choice);
        choice.setQuestion(null);

    }

}
