package ITNews.project.ITNews.exeption;

import ITNews.project.ITNews.dto.ErrorApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;;import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
public class DefaultExceptionHandler extends DefaultResponseErrorHandler {

    private static final Logger log = Logger.getLogger(DefaultExceptionHandler.class.getName());

    @ExceptionHandler({ Exception.class, RuntimeException.class})
    public void anyException(Exception e) {
        log.log(Level.SEVERE,"Exception", e);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorApi badCredentials() {
        ErrorApi errorApi = new ErrorApi();
        errorApi.setCodeError(HttpStatus.UNAUTHORIZED.value());
        errorApi.setMessage("Invalid credentials!");
        return errorApi;
    }
}
