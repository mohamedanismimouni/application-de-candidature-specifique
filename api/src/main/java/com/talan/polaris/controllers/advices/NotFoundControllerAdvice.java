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
import com.talan.polaris.exceptions.AccountNotFoundException;
import com.talan.polaris.exceptions.NotFoundException;
import com.talan.polaris.exceptions.ResourceNotFoundException;
import com.talan.polaris.utils.HttpUtils;
import static com.talan.polaris.constants.MessagesConstants.ERROR_NOT_FOUND_RESOURCE_NOT_FOUND_FOR_ID;
import static com.talan.polaris.constants.MessagesConstants.ERROR_NOT_FOUND_RESOURCE_NOT_FOUND;
import static com.talan.polaris.constants.MessagesConstants.ERROR_NOT_FOUND_ACCOUNT_NOT_FOUND;
/**
 * NotFoundControllerAdvice.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@ControllerAdvice
public class NotFoundControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotFoundControllerAdvice.class);
    private final MessageSource messageSource;
    @Autowired
    public NotFoundControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = { NotFoundException.class })
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException notFoundException,
            HttpServletRequest request) {

        LOGGER.debug("NotFoundException handler is invoked...");

        ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder(
                HttpStatus.NOT_FOUND,
                HttpUtils.obtainRequestPath(request));

        if (notFoundException instanceof ResourceNotFoundException) {

            LOGGER.debug("ResourceNotFoundException detected");

            ResourceNotFoundException resourceNotFoundException = (ResourceNotFoundException) notFoundException;

            if (resourceNotFoundException.getId() == null) {

                errorResponseBuilder.withMessage(this.messageSource.getMessage(
                        ERROR_NOT_FOUND_RESOURCE_NOT_FOUND,
                        null,
                        request.getLocale()));

            } else {

                errorResponseBuilder.withMessage(String.format(
                        this.messageSource.getMessage(
                                ERROR_NOT_FOUND_RESOURCE_NOT_FOUND_FOR_ID,
                                null,
                                request.getLocale()),
                        resourceNotFoundException.getId()));

            }

        } else if (notFoundException instanceof AccountNotFoundException) {

            LOGGER.debug("AccountNotFoundException detected");

            errorResponseBuilder.withMessage(String.format(
                    this.messageSource.getMessage(
                            ERROR_NOT_FOUND_ACCOUNT_NOT_FOUND,
                            null,
                            request.getLocale()),
                    ((AccountNotFoundException) notFoundException).getEmail()));

        }

        ErrorResponse errorResponse = errorResponseBuilder.build();

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatus()));

    }

}
