package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.application.exceptions.HandledException;

public class ContainsLetterValidator extends Validator<String> {
    public ContainsLetterValidator(HandledException e) {
        super(e);
    }

    @Override
    public void validate(String value) {
        if (!containsLetter(value))
            throw e;
        super.validate(value);
    }

    private boolean containsLetter(String value) {
        for (char c : value.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }
}
