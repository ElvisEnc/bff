package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.application.exceptions.HandledException;

public class MinLengthValidator extends Validator<String> {
    private final int length;

    public MinLengthValidator(int length, HandledException e) {
        super(e);
        this.length = length;
    }

    @Override
    public void validate(String value) {
        if (value.length() < length)
            throw e;
        super.validate(value);
    }
}
