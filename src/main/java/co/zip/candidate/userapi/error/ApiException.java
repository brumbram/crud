package co.zip.candidate.userapi.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class ApiException extends RuntimeException {

    private final String code;
    private final String title;
    private final String detail;

    public ApiException(Constants.Errors error) {
        this.code = error.getCode();
        this.title = error.getTitle();
        this.detail = error.getDetail();
    }
}
