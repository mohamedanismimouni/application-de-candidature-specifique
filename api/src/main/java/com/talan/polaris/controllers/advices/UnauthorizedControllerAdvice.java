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
import com.talan.polaris.exceptions.UnauthorizedException;
import com.talan.polaris.exceptions.WrongPasswordException;
import com.talan.polaris.utils.HttpUtils;
import static com.talan.polaris.constants.MessagesConstants.ERROR_UNAUTHORIZED_WRONG_PASSWORD;
/**
 * UnauthorizedControllerAdvice.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@ControllerAdvice
public class UnauthorizedControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnauthorizedControllerAdvice.class);

    private final MessageSource messageSource;

    @Autowired
    public UnauthorizedControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = { UnauthorizedException.class })
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            UnauthorizedException unauthorizedException,
            HttpServletRequest request) {

        LOGGER.debug("UnauthorizedException handler is invoked...");

        ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder(
                HttpStatus.UNAUTHORIZED,
                HttpUtils.obtainRequestPath(request));

        if (unauthorizedException instanceof WrongPasswordException) {

            LOGGER.debug("WrongPasswordException detected");

            errorResponseBuilder.withMessage(this.messageSource.getMessage(
                    ERROR_UNAUTHORIZED_WRONG_PASSWORD,
                    null,
                    request.getLocale()));

        }

        ErrorResponse errorResponse = errorResponseBuilder.build();

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatus()));

    }

}
