package bg.com.bo.bff.commons.validators.debit.card;

import bg.com.bo.bff.commons.annotations.debit.card.ValidType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class TypeValidator implements ConstraintValidator<ValidType, Integer> {
    private final List<Integer> allowedValues = Arrays.asList(0, 4, 5, 7);

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && allowedValues.contains(value);
    }
}
