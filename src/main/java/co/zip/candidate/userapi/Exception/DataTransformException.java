package co.zip.candidate.userapi.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Error while transforming json data response")
public class DataTransformException extends RuntimeException {

    public DataTransformException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataTransformException(String message) { super(message); }
}
