package com.talan.polaris.controllers.advices;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.talan.polaris.dto.ErrorResponse;
import com.talan.polaris.dto.ErrorResponse.ErrorResponseBuilder;
import com.talan.polaris.exceptions.AccountActivationException;
import com.talan.polaris.exceptions.AccountActivationMailSendingException;
import com.talan.polaris.exceptions.AccountNotActiveException;
import com.talan.polaris.exceptions.AccountStatusUpdateException;
import com.talan.polaris.exceptions.ForbiddenException;
import com.talan.polaris.exceptions.PasswordResetException;
import com.talan.polaris.utils.HttpUtils;

import static com.talan.polaris.constants.MessagesConstants.ERROR_FORBIDDEN_ACCOUNT_NOT_ACTIVE;
import static com.talan.polaris.constants.MessagesConstants.ERROR_FORBIDDEN_ACCOUNT_ACTIVATION;
import static com.talan.polaris.constants.MessagesConstants.ERROR_FORBIDDEN_ACCOUNT_ACTIVATION_MAIL_SENDING;
import static com.talan.polaris.constants.MessagesConstants.ERROR_FORBIDDEN_ACCOUNT_STATUS_UPDATE;
import static com.talan.polaris.constants.MessagesConstants.ERROR_FORBIDDEN_PASSWORD_RESET;

/**
 * ForbiddenControllerAdvice.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@ControllerAdvice
public class ForbiddenControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForbiddenControllerAdvice.class);

    private final MessageSource messageSource;

    @Autowired
    public ForbiddenControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = { ForbiddenException.class })
    public ResponseEntity<ErrorResponse> handleForbiddenException(
            ForbiddenException forbiddenException,
            HttpServletRequest request) {

        LOGGER.debug("ForbiddenException handler is invoked...");

        ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder(
                HttpStatus.FORBIDDEN,
                HttpUtils.obtainRequestPath(request));

        if (forbiddenException instanceof AccountNotActiveException) {

            LOGGER.debug("AccountNotActiveException detected");

            errorResponseBuilder.withMessage(this.messageSource.getMessage(
                    ERROR_FORBIDDEN_ACCOUNT_NOT_ACTIVE,
                    null,
                    request.getLocale()));

        } else if (forbiddenException instanceof AccountActivationException) {

            LOGGER.debug("AccountActivationException detected");

            errorResponseBuilder.withMessage(this.messageSource.getMessage(
                    ERROR_FORBIDDEN_ACCOUNT_ACTIVATION,
                    null,
                    request.getLocale()));

        } else if (forbiddenException instanceof AccountActivationMailSendingException) {

            LOGGER.debug("AccountActivationMailSendingException detected");

            errorResponseBuilder.withMessage(this.messageSource.getMessage(
                    ERROR_FORBIDDEN_ACCOUNT_ACTIVATION_MAIL_SENDING,
                    null,
                    request.getLocale()));

        } else if (forbiddenException instanceof AccountStatusUpdateException) {

            LOGGER.debug("AccountStatusUpdateException detected");

            errorResponseBuilder.withMessage(this.messageSource.getMessage(
                    ERROR_FORBIDDEN_ACCOUNT_STATUS_UPDATE,
                    null,
                    request.getLocale()));

        } else if (forbiddenException instanceof PasswordResetException) {

            LOGGER.debug("PasswordResetException detected");

            errorResponseBuilder.withMessage(this.messageSource.getMessage(
                    ERROR_FORBIDDEN_PASSWORD_RESET,
                    null,
                    request.getLocale()));

        }

        ErrorResponse errorResponse = errorResponseBuilder.build();

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatus()));

    }

}
