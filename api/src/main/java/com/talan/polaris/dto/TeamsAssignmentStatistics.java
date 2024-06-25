package com.talan.polaris.dto;

import java.io.Serializable;

/**
 * TeamsAssignmentStatistics.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public final class TeamsAssignmentStatistics implements Serializable {

    private static final long serialVersionUID = 7550714162358425049L;

    private final int assignedCollaborators;
    private final int unassignedCollaborators;
    private final int assignedManagers;
    private final int unassignedManagers;

    public TeamsAssignmentStatistics(
        int assignedCollaborators,
        int unassignedCollaborators,
        int assignedManagers,
        int unassignedManagers
    ) {

        this.assignedCollaborators = assignedCollaborators;
        this.unassignedCollaborators = unassignedCollaborators;
        this.assignedManagers = assignedManagers;
        this.unassignedManagers = unassignedManagers;

    }

    public int getAssignedCollaborators() {
        return this.assignedCollaborators;
    }

    public int getUnassignedCollaborators() {
        return this.unassignedCollaborators;
    }

    public int getAssignedManagers() {
        return this.assignedManagers;
    }

    public int getUnassignedManagers() {
        return this.unassignedManagers;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("TeamsAssignmentStatistics").append("\n")
                .append("assignedCollaborators   = ").append(this.assignedCollaborators).append("\n")
                .append("unassignedCollaborators = ").append(this.unassignedCollaborators).append("\n")
                .append("assignedManagers        = ").append(this.assignedManagers).append("\n")
                .append("unassignedManagers      = ").append(this.unassignedManagers)
                .toString();

    }

}
