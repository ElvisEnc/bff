package bg.com.bo.bff.commons.validators;

import bg.com.bo.bff.commons.annotations.OnlyNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PersonIdValidator implements ConstraintValidator<OnlyNumber, String> {
    private String notNullMessage;
    private String onlyNumberMessage;
    private String greaterThanZeroMessage;
    private String maxLengthMessage;

    @Override
    public void initialize(OnlyNumber constraintAnnotation) {
        this.notNullMessage = constraintAnnotation.notNullMessage();
        this.onlyNumberMessage = constraintAnnotation.onlyNumberMessage();
        this.greaterThanZeroMessage = constraintAnnotation.greaterThanZeroMessage();
        this.maxLengthMessage = constraintAnnotation.maxLengthMessage();
    }

    @Override
    public boolean isValid(String personId, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (personId == null || personId.trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate(notNullMessage)
                    .addConstraintViolation();
            return false;
        }

        if (!personId.matches("\\d+")) {
            context.buildConstraintViolationWithTemplate(onlyNumberMessage)
                    .addConstraintViolation();
            return false;
        }

        if (personId.length() > 18) {
            context.buildConstraintViolationWithTemplate(maxLengthMessage)
                    .addConstraintViolation();
            return false;
        }

        try {
            long value = Long.parseLong(personId);
            if (value <= 0) {
                context.buildConstraintViolationWithTemplate(greaterThanZeroMessage)
                        .addConstraintViolation();
                return false;
            }
        } catch (NumberFormatException e) {
            context.buildConstraintViolationWithTemplate(onlyNumberMessage)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
