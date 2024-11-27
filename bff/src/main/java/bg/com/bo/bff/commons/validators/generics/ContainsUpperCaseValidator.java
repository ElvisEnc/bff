package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.application.exceptions.HandledException;

public class ContainsUpperCaseValidator extends Validator<String> {
    public ContainsUpperCaseValidator(HandledException e) {
        super(e);
    }

    @Override
    public void validate(String value) {
        if (!containsUppercaseLetter(value))
            throw e;
        super.validate(value);
    }

    private boolean containsUppercaseLetter(String value) {
        for (char c : value.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }
}
