package tw.waterballsa.gaas.saboteur.spring.advices;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tw.waterballsa.gaas.saboteur.domain.exceptions.NotFoundException;
import tw.waterballsa.gaas.saboteur.domain.exceptions.SaboteurGameException;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class SaboteurAdvice {
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({SaboteurGameException.class})
    public String badRequest(SaboteurGameException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map(ObjectError::getDefaultMessage)
            .filter(StringUtils::isNotBlank)
            .collect(joining("\n"));
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public String notFound(NotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public String otherException(Exception exception) {
        return exception.getMessage();
    }
}
