package ITNews.project.demo.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({SpringException.class, MyExeption.class, Exception.class, RuntimeException.class})
    public void anyException(Exception e) {
        System.out.println(e);
    }
    public ResponseEntity<Object> message(MyExeption err){
        return new ResponseEntity<>(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
