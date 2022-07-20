package co.zip.candidate.userapi.error;

import org.springframework.validation.FieldError;

public class InvalidFieldMapper {

    public static ErrorDetail toErrorDetails(FieldError field) {

        switch(field.getCode()) {
            case "NotNull" :
                return getErrorDetail(Constants.Errors.MISSING_FIELD_INPUT, field.getField());
            case "ValidEmail" :
                return getErrorDetail(Constants.Errors.INVALID_EMAIL, field.getField());
            case "ValueOfEnum" :
                return getErrorDetail(Constants.Errors.INVALID_FIELD_INPUT, field.getField());
            default :
                return getErrorDetail(Constants.Errors.GENERAL_ERROR);
        }
    }

    private static ErrorDetail getErrorDetail(Constants.Errors error) {
        return new ErrorDetail(error.getCode(), error.getTitle(), error.getDetail());
    }

    private static ErrorDetail getErrorDetail(Constants.Errors error, String fieldName) {
        return new ErrorDetail(error.getCode(), error.getTitle(), String.format(error.getDetail(), fieldName));
    }
}
