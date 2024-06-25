package com.talan.polaris.controllers.advices;
import java.sql.SQLException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.talan.polaris.dto.ErrorResponse;
import com.talan.polaris.dto.ErrorResponse.ErrorResponseBuilder;
import com.talan.polaris.utils.HttpUtils;
import static com.talan.polaris.constants.CommonConstants.SQL_STATE_FOREIGN_KEY_INTEGRITY_VIOLATION;
import static com.talan.polaris.constants.CommonConstants.SQL_STATE_UNIQUE_CONSTRAINT_VIOLATION;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_DATA_INTEGRITY_VIOLATION;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_UNIQUE_CONSTRAINT_VIOLATION;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_FOREIGN_KEY_INTEGRITY_VIOLATION;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_INVALID_FORMAT_JSON_PATCH_PARSING;
/**
 * BadRequestControllerAdvice.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@ControllerAdvice
public class BadRequestControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(BadRequestControllerAdvice.class);

    private final MessageSource messageSource;

    @Autowired
    public BadRequestControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException,
            HttpServletRequest request) {

        LOGGER.debug("DataIntegrityViolationException handler is invoked...");

        ErrorResponseBuilder errorResponseBuilder;

        if (dataIntegrityViolationException.getMostSpecificCause() instanceof SQLException &&
                SQL_STATE_FOREIGN_KEY_INTEGRITY_VIOLATION.equals(
                        ((SQLException) dataIntegrityViolationException.getMostSpecificCause()).getSQLState())) {

            LOGGER.debug("Foreign key integrity violation detected");

            errorResponseBuilder = new ErrorResponseBuilder(
                    HttpStatus.BAD_REQUEST,
                    HttpUtils.obtainRequestPath(request))
                    .withMessage(this.messageSource.getMessage(
                            ERROR_BAD_REQUEST_FOREIGN_KEY_INTEGRITY_VIOLATION,
                            null,
                            request.getLocale()));

        } else if (dataIntegrityViolationException.getMostSpecificCause() instanceof SQLException &&
                SQL_STATE_UNIQUE_CONSTRAINT_VIOLATION.equals(
                        ((SQLException) dataIntegrityViolationException.getMostSpecificCause()).getSQLState())) {

            LOGGER.debug("Unique constraint violation detected");

            errorResponseBuilder = new ErrorResponseBuilder(
                    HttpStatus.CONFLICT,
                    HttpUtils.obtainRequestPath(request))
                    .withMessage(this.messageSource.getMessage(
                            ERROR_BAD_REQUEST_UNIQUE_CONSTRAINT_VIOLATION,
                            null,
                            request.getLocale()));

        } else {

            LOGGER.debug("No specific DataIntegrityViolationException is detected");

            errorResponseBuilder = new ErrorResponseBuilder(
                    HttpStatus.BAD_REQUEST,
                    HttpUtils.obtainRequestPath(request))
                    .withMessage(this.messageSource.getMessage(
                            ERROR_BAD_REQUEST_DATA_INTEGRITY_VIOLATION,
                            null,
                            request.getLocale()));

        }

        ErrorResponse errorResponse = errorResponseBuilder.build();

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatus()));

    }

    @ExceptionHandler(value = { InvalidDataAccessApiUsageException.class })
    public ResponseEntity<ErrorResponse> handleInvalidDataAccessApiUsageException(
            InvalidDataAccessApiUsageException invalidDataAccessApiUsageException,
            HttpServletRequest request) {

        LOGGER.debug("InvalidDataAccessApiUsageException handler is invoked...");

        ErrorResponse errorResponse = new ErrorResponseBuilder(
                HttpStatus.BAD_REQUEST,
                HttpUtils.obtainRequestPath(request))
                .withMessage(this.messageSource.getMessage(
                        ERROR_BAD_REQUEST_DATA_INTEGRITY_VIOLATION,
                        null,
                        request.getLocale()))
                .withDetails(invalidDataAccessApiUsageException.getMostSpecificCause().getMessage())
                .build();

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatus()));

    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException entityNotFoundException,
            HttpServletRequest request) {

        LOGGER.debug("EntityNotFoundException handler is invoked...");

        ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder(
                HttpStatus.BAD_REQUEST,
                HttpUtils.obtainRequestPath(request))
                .withMessage(this.messageSource.getMessage(
                        ERROR_BAD_REQUEST_DATA_INTEGRITY_VIOLATION,
                        null,
                        request.getLocale()));

        if (entityNotFoundException.getCause() != null) {
            errorResponseBuilder.withDetails(entityNotFoundException.getCause().getMessage());
        } else {
            errorResponseBuilder.withDetails(entityNotFoundException.getMessage());
        }

        ErrorResponse errorResponse = errorResponseBuilder.build();

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatus()));

    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException,
            HttpServletRequest request) {

        LOGGER.debug("ConstraintViolationException handler is invoked...");

        ErrorResponse errorResponse = new ErrorResponseBuilder(
                HttpStatus.BAD_REQUEST,
                HttpUtils.obtainRequestPath(request))
                .withMessage(this.messageSource.getMessage(
                        ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION,
                        null,
                        request.getLocale()))
                .withDetails(constraintViolationException.getConstraintViolations().toString())
                .build();

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatus()));

    }

    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException httpMessageNotReadableException,
            HttpServletRequest request) {

        LOGGER.debug("HttpMessageNotReadableException handler is invoked...");

        if (httpMessageNotReadableException.contains(InvalidFormatException.class)) {

            LOGGER.info("InvalidFormatException detected");

            ErrorResponse errorResponse = new ErrorResponseBuilder(
                    HttpStatus.BAD_REQUEST,
                    HttpUtils.obtainRequestPath(request))
                    .withMessage(this.messageSource.getMessage(
                            ERROR_BAD_REQUEST_INVALID_FORMAT_JSON_PATCH_PARSING,
                            null,
                            request.getLocale()))
                    .withDetails(httpMessageNotReadableException.getMostSpecificCause()
                            .getMessage())
                    .build();

            return new ResponseEntity<>(
                    errorResponse,
                    HttpStatus.valueOf(errorResponse.getStatus()));

        } else {
            LOGGER.info("HttpMessageNotReadableException handler failed to handle any specific exception");
            throw httpMessageNotReadableException;
        }

    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException illegalArgumentException,
            HttpServletRequest request) {

        LOGGER.debug("IllegalArgumentException handler is invoked...");

        String message;

        try {

            message = this.messageSource.getMessage(
                    illegalArgumentException.getMessage(),
                    null,
                    request.getLocale());

        } catch (NoSuchMessageException e) {

            message = this.messageSource.getMessage(
                    ERROR_BAD_REQUEST,
                    null,
                    request.getLocale());

        }

        ErrorResponse errorResponse = new ErrorResponseBuilder(
                HttpStatus.BAD_REQUEST,
                HttpUtils.obtainRequestPath(request))
                .withMessage(message)
                .build();

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatus()));

    }

}
