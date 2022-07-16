package co.zip.candidate.userapi.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {

    String message() default "Incorrect email address provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
