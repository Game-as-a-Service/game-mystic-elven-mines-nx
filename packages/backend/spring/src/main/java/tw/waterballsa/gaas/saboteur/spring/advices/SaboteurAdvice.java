package tw.waterballsa.gaas.saboteur.spring.advices;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tw.waterballsa.gaas.saboteur.domain.exceptions.SaboteurGameException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class SaboteurAdvice {
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({ SaboteurGameException.class })
    public String badRequest(SaboteurGameException exception) {
        return exception.getMessage();
    }
}
