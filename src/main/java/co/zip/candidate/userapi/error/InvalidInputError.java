package co.zip.candidate.userapi.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid input found in request")
public class InvalidInputError extends ApiException {

    public InvalidInputError(Constants.Errors error) { super (error); }
}
