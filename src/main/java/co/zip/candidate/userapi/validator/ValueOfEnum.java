package co.zip.candidate.userapi.validator;

import lombok.extern.slf4j.Slf4j;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
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

    @Slf4j
    class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
        private List<String> acceptedValues;
        private ValueOfEnum constraintViolation;

        @Override
        public void initialize(ValueOfEnum constraintAnnotation) {
            this.constraintViolation = constraintViolation;
            acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                                   .map(Enum::name)
                                   .collect(Collectors.toList());
        }

        @Override
        public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
            log.debug("value {} acceptedValues {} ",value, acceptedValues );
            return value != null && acceptedValues.contains(value.toString());
        }
    }
}