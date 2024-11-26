package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.application.exceptions.HandledException;

public class ContainsDigitValidator extends Validator<String> {
    public ContainsDigitValidator(HandledException e) {
        super(e);
    }

    @Override
    public void validate(String value) {
        if (!containsDigit(value))
            throw e;
        super.validate(value);
    }

    private boolean containsDigit(String value) {
        for (char c : value.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}
