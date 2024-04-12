package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.application.exceptions.HandledException;

public class ContainsDigitValidator extends Validator<String> {
    public ContainsDigitValidator(HandledException e) {
        super(e);
    }

    @Override
    public void validate(String value) {
        if (!value.matches(".*\\d.*"))
            throw e;
        super.validate(value);
    }
}
