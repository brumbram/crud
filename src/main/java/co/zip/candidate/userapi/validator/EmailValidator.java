package co.zip.candidate.userapi.validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private ValidEmail constraintAnnotation;

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        try {
            // Create InternetAddress object and validated the supplied email address.
            InternetAddress internetAddress = new InternetAddress(email, true); // strict
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            System.out.println("Bad eMail address: " + email);
        }

        return false;
    }
}