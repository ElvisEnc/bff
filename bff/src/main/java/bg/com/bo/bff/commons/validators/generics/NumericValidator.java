package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.commons.annotations.Numeric;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.NotImplementedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class NumericValidator implements ConstraintValidator<Numeric, String> {
    private String notNullMessage;
    private String onlyNumberMessage;
    private String greaterThanMessage;
    private String lowerThanMessage;
    private String notValidNumberMessage;
    private String min;
    private String max;
    private Class<? extends Number> numericType;
    private String fieldName;

    @Override
    public void initialize(Numeric constraintAnnotation) {
        this.notNullMessage = constraintAnnotation.notNullMessage();
        this.onlyNumberMessage = constraintAnnotation.onlyNumberMessage();
        this.greaterThanMessage = constraintAnnotation.greaterThanMessage();
        this.lowerThanMessage = constraintAnnotation.lowerThanMessage();
        this.notValidNumberMessage = constraintAnnotation.notValidNumberMessage();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.numericType = constraintAnnotation.numericType();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (nullVerification(value, context)) return false;

        if (numericVerification(value, context)) return false;

        if (maxVerification(value, context)) return false;

        return !minVerification(value, context);
    }

    private String setMessage(String originalMessage) {
        if (fieldName != null && !fieldName.isBlank())
            return String.format("%s:%s", fieldName, originalMessage);
        else
            return originalMessage;
    }

    private boolean minVerification(String value, ConstraintValidatorContext context) {
        if (min != null && !min.isBlank()) {
            try {
                if (compareNumeric(value, min) < 0) {
                    context.buildConstraintViolationWithTemplate(setMessage(String.format(greaterThanMessage, min)))
                            .addConstraintViolation();
                    return true;
                }
            } catch (NumberFormatException e) {
                context.buildConstraintViolationWithTemplate(setMessage(onlyNumberMessage))
                        .addConstraintViolation();
                return true;
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                context.buildConstraintViolationWithTemplate(setMessage(notValidNumberMessage))
                        .addConstraintViolation();
                return true;
            }
        }
        return false;
    }

    private boolean maxVerification(String value, ConstraintValidatorContext context) {
        if (max != null && !max.isBlank()) {
            try {
                if (compareNumeric(value, max) > 0) {
                    context.buildConstraintViolationWithTemplate(setMessage(String.format(lowerThanMessage, max)))
                            .addConstraintViolation();
                    return true;
                }
            } catch (NumberFormatException e) {
                context.buildConstraintViolationWithTemplate(setMessage(onlyNumberMessage))
                        .addConstraintViolation();
                return true;
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                context.buildConstraintViolationWithTemplate(setMessage(notValidNumberMessage))
                        .addConstraintViolation();
                return true;
            }
        }
        return false;
    }

    private int compareNumeric(String value, String valueToCompare) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Number valueParsed = parseNumber(value);
        Number valueToCompareParsed = parseNumber(valueToCompare);

        Method method = valueParsed.getClass().getMethod("compareTo", valueParsed.getClass());

        return (int) method.invoke(valueParsed, valueToCompareParsed);
    }

    private Number parseNumber(String value) {
        if (numericType == BigDecimal.class)
            return new BigDecimal(value);
        else
            throw new NotImplementedException("Number class for parseNumber() not defined.");
    }

    private boolean numericVerification(String value, ConstraintValidatorContext context) {
        if (numericType == BigDecimal.class) {
            if (!value.matches("-?(\\d+|\\d+.\\d\\d?)")) {
                context.buildConstraintViolationWithTemplate(setMessage(onlyNumberMessage))
                        .addConstraintViolation();
                return true;
            }
        } else
            throw new NotImplementedException("Number class for numericVerification() not defined.");
        return false;
    }

    private boolean nullVerification(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate(setMessage(notNullMessage))
                    .addConstraintViolation();
            return true;
        }
        return false;
    }
}
