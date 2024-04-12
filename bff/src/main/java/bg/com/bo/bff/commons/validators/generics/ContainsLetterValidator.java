package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.application.exceptions.HandledException;

public class ContainsLetterValidator extends Validator<String> {
    public ContainsLetterValidator(HandledException e) {
        super(e);
    }

    @Override
    public void validate(String value) {
        if (!value.matches(".*[a-z].*"))
            throw e;
        super.validate(value);
    }
}
