package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.application.exceptions.HandledException;

import java.util.Objects;

public class NotEqualsValidator<T> extends Validator<T> {
    private final T comparable;

    public NotEqualsValidator(T comparable, HandledException e) {
        super(e);
        this.comparable = comparable;
    }

    @Override
    public void validate(T value) {
        if (Objects.equals(value, comparable))
            throw e;
        super.validate(value);
    }
}
