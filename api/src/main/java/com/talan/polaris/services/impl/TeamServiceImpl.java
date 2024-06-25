package com.talan.polaris.services.impl;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import javax.json.JsonException;
import javax.json.JsonPatch;
import javax.json.JsonStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talan.polaris.entities.EvaluationEntity;
import com.talan.polaris.entities.TeamEntity;
import com.talan.polaris.enumerations.EvaluationStatusEnum;
import com.talan.polaris.repositories.TeamRepository;
import com.talan.polaris.services.EvaluationService;
import com.talan.polaris.services.TeamService;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_JSON_PATCH;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_TEAM_TEAM_EVALUATION_DATE_VALIDITY_CONSTRAINT;

/**
 * An implementation of {@link TeamService}, containing business methods
 * implementations specific to {@link TeamEntity}, and may override some
 * of the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class TeamServiceImpl
        extends GenericServiceImpl<TeamEntity>
        implements TeamService {

    private final TeamRepository teamRepository;
    private final EvaluationService evaluationService;
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);

    @Autowired
    public TeamServiceImpl(
            TeamRepository repository,
            EvaluationService evaluationService,
            ObjectMapper objectMapper) {

        super(repository);
        this.teamRepository = repository;
        this.evaluationService = evaluationService;
        this.objectMapper = objectMapper;

    }

    @Override
    public TeamEntity findTeamByManagerId(Long managerId) {
        LOGGER.info("find Team By Manager ID");
        return this.teamRepository.findTeamByManagerId(managerId);
    }

    @Override
    public TeamEntity createTeam(TeamEntity team) {
        LOGGER.info("create Team");
        team.setTeamEvaluationDate(null);
        return create(team);
    }

    @Override
    @Transactional
    public TeamEntity partialUpdateTeam(String teamId, JsonPatch jsonPatch) {
        LOGGER.info("partial update Team");
        TeamEntity team = findById(teamId);

        JsonStructure targetJson = objectMapper.convertValue(
                new TeamEntity(),
                JsonStructure.class);

        JsonStructure patchedJson;
        try {
            patchedJson = jsonPatch.apply(targetJson);
        } catch (JsonException e) {
            throw new IllegalArgumentException(ERROR_BAD_REQUEST_JSON_PATCH, e);
        }

        TeamEntity patchedTeam = this.objectMapper.convertValue(
                patchedJson,
                TeamEntity.class);

        if (patchedTeam.getTeamEvaluationDate() != null) {

            MonthDay teamEvaluationMonthDay;

            try {
                teamEvaluationMonthDay = MonthDay.parse(patchedTeam.getTeamEvaluationDate());
            } catch(DateTimeParseException e) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_TEAM_TEAM_EVALUATION_DATE_VALIDITY_CONSTRAINT, e);

            }

            team.setTeamEvaluationDate(patchedTeam.getTeamEvaluationDate());

            LocalDate upcomingEvaluationsDate = teamEvaluationMonthDay.atYear(Year.now().getValue());

            if (upcomingEvaluationsDate.isBefore(LocalDate.now())) {
                upcomingEvaluationsDate = upcomingEvaluationsDate.plusYears(1);
            }

            Collection<EvaluationEntity> openTeamEvaluations = this.evaluationService.findTeamEvaluations(
                    teamId,
                    EvaluationStatusEnum.OPEN);

            for (EvaluationEntity openEvaluation : openTeamEvaluations) {
                openEvaluation.setEvaluationDate(upcomingEvaluationsDate);
            }

        }

        return team;

    }

    @Override
    public void updateTeamName(Long teamManagerId, String name) {
        LOGGER.info("update Team Name");
        TeamEntity teamEntity = findTeamByManagerId(teamManagerId);
        teamEntity.setName(name);
        update(teamEntity);


    }

    @Override
    public TeamEntity findTeamByName(String teamName) {
        return this.teamRepository.findTeamByName(teamName);
    }

}
