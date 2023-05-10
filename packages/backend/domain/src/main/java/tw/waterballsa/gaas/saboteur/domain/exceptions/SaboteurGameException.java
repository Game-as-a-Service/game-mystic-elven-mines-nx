package tw.waterballsa.gaas.saboteur.domain.exceptions;

public class SaboteurGameException extends RuntimeException {

    public SaboteurGameException(String message) {
        super(message);
    }

    public SaboteurGameException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaboteurGameException(Throwable cause) {
        super(cause);
    }

    protected SaboteurGameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
