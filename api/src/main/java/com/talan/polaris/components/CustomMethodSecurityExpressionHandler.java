package com.talan.polaris.components;

import com.talan.polaris.services.*;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * CustomMethodSecurityExpressionHandler.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Component
public class CustomMethodSecurityExpressionHandler
        extends DefaultMethodSecurityExpressionHandler {

    private final CollaboratorService userService;
    private final OnboardingService onboardingService;
    private final MentorshipService mentorshipService;
    private final ResponseService responseService;
    private final EvaluationService evaluationService;
    private final CareerPositionService careerPositionService;
    private final TeamService teamService;

    @Autowired
    public CustomMethodSecurityExpressionHandler(
            CollaboratorService userService,
            OnboardingService onboardingService,
            MentorshipService mentorshipService,
            ResponseService responseService,
            EvaluationService evaluationService,
            CareerPositionService careerPositionService,
            TeamService teamService) {

        super();
        this.userService = userService;
        this.onboardingService = onboardingService;
        this.mentorshipService = mentorshipService;
        this.responseService = responseService;
        this.evaluationService = evaluationService;
        this.careerPositionService = careerPositionService;
        this.teamService = teamService;

    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication,
            MethodInvocation invocation) {

        CustomMethodSecurityExpressionRoot root = new CustomMethodSecurityExpressionRoot(
            authentication,
            this.userService,
            this.onboardingService,
            this.mentorshipService,
            this.responseService,
            this.evaluationService,
            this.careerPositionService,
            this.teamService);

        root.setThis(invocation.getThis());
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(getTrustResolver());
        root.setRoleHierarchy(getRoleHierarchy());
        root.setDefaultRolePrefix(getDefaultRolePrefix());

        return root;

    }

}
