package tw.waterballsa.gaas.saboteur.spring.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tw.waterballsa.gaas.saboteur.domain.exceptions.IllegalRequestException;
import tw.waterballsa.gaas.saboteur.domain.exceptions.NotFoundException;
import tw.waterballsa.gaas.saboteur.domain.exceptions.SaboteurGameException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class SaboteurAdvice {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({SaboteurGameException.class,
        IllegalRequestException.class})
    public String badRequest(RuntimeException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public String notFound(NotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public String otherException(Exception exception) {
        return exception.getMessage();
    }
}
