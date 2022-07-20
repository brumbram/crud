package co.zip.candidate.userapi.validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Target({METHOD, FIELD, PARAMETER, ANNOTATION_TYPE})
@Constraint(validatedBy = ValidEmail.EmailValidator.class)
@Retention(RUNTIME)
public @interface ValidEmail {

    String message() default "Incorrect email address provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class EmailValidator implements ConstraintValidator<ValidEmail, String> {

        private ValidEmail constraintAnnotation;

        @Override
        public void initialize(ValidEmail constraintAnnotation) {
            this.constraintAnnotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(String email, ConstraintValidatorContext context) {
            if (email == null) {
                return false;
            }
            try {
                // Create InternetAddress object and validated the supplied email address.
                InternetAddress internetAddress = new InternetAddress(email, true); // strict
                internetAddress.validate();
                return true;
            } catch (AddressException e) {}
            return false;
        }
    }
}
