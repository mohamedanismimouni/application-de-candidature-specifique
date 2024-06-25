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
import com.talan.polaris.exceptions.MailSendingException;
import com.talan.polaris.exceptions.ServiceUnavailableException;
import com.talan.polaris.utils.HttpUtils;
import static com.talan.polaris.constants.MessagesConstants.ERROR_SERVICE_UNAVAILABLE_MAIL_SENDING;
/**
 * ServiceUnavailableControllerAdvice.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@ControllerAdvice
public class ServiceUnavailableControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUnavailableControllerAdvice.class);

    private final MessageSource messageSource;

    @Autowired
    public ServiceUnavailableControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = { ServiceUnavailableException.class })
    public ResponseEntity<ErrorResponse> handleServiceUnavailableException(
            final ServiceUnavailableException serviceUnavailableException,
            final HttpServletRequest request) {

        LOGGER.debug("ServiceUnavailableException handler is invoked...");

        final ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder(
                HttpStatus.SERVICE_UNAVAILABLE,
                HttpUtils.obtainRequestPath(request));

        if (serviceUnavailableException instanceof MailSendingException) {

            LOGGER.debug("MailSendingException detected");

            errorResponseBuilder.withMessage(this.messageSource.getMessage(
                    ERROR_SERVICE_UNAVAILABLE_MAIL_SENDING,
                    null,
                    request.getLocale()));

        }

        final ErrorResponse errorResponse = errorResponseBuilder.build();

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatus()));

    }

}
