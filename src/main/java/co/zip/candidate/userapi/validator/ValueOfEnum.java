package co.zip.candidate.userapi.validator;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, PARAMETER, ANNOTATION_TYPE})
@Constraint(validatedBy = ValueOfEnum.ValueOfEnumValidator.class)
@Retention(RUNTIME)
public @interface ValueOfEnum {

    Class<? extends Enum<?>> enumClass();
    String message() default "must be any of enum {enumClass}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


    class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
        private List<String> acceptedValues;
        private ValueOfEnum constraintAnnotation;

        @Override
        public void initialize(ValueOfEnum constraintAnnotation) {
            this.constraintAnnotation = constraintAnnotation;
            acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                                   .map(Enum::name)
                                   .collect(Collectors.toList());
        }

        @Override
        public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
            if (value == null) {
                return false;
            }

            return acceptedValues.contains(value.toString());
        }
    }
}