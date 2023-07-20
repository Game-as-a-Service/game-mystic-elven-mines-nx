package tw.waterballsa.gaas.saboteur.domain.exceptions;

public class IllegalRequestException extends RuntimeException {

    public IllegalRequestException(String message) {
        super(message);
    }

}
