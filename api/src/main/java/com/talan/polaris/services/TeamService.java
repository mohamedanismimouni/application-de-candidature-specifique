package com.talan.polaris.services;

import javax.json.JsonPatch;

import com.talan.polaris.entities.TeamEntity;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link TeamEntity}.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface TeamService extends GenericService<TeamEntity> {

    /**
     * Finds the team whose manager having the given {@code managerId}.
     *
     * @param managerId
     *
     * @return the team matching the given parameter if any.
     */
    public TeamEntity findTeamByManagerId(Long managerId);

    /**
     * Creates the given {@code team}.
     * <p>
     * Sets the field {@code teamEvaluationDate} to null, because it can be
     * set only through the update operation.
     *
     * @param team
     *
     * @return the created team.
     */
    public TeamEntity createTeam(TeamEntity team);

    /**
     * Partially updates an {@link TeamEntity}.
     * <p>
     * The fields concerned by this update operation are:
     * {@code teamEvaluationDate}.
     * <p>
     * Updating the {@code teamEvaluationDate} will also update evaluation dates
     * of all {@code OPEN} evaluations of this team's members.
     *
     * @param teamId
     * @param jsonPatch
     *
     * @return the updated team.
     *
     * @throws IllegalArgumentException if the JSON patch application fails, or
     * if the specified team evaluation date is not valid.
     */
    public TeamEntity partialUpdateTeam(String teamId, JsonPatch jsonPatch);

    public void updateTeamName(Long managerId, String name);

    /**
     * Finds the team whose name having the given {@code teamName}.
     *
     * @param teamName
     *
     * @return the team matching the given parameter if any.
     */
    public TeamEntity findTeamByName(String teamName);

}
