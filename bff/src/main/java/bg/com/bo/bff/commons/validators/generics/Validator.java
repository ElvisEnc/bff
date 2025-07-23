package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.application.exceptions.HandledException;

public abstract class Validator<T> implements IValidator<T> {
    @lombok.Getter
    private IValidator<T> next;

    protected HandledException e;

    public Validator(HandledException e) {
        this.e = e;
    }

    @Override
    public void validate(T value) {
        if (next != null)
            next.validate(value);
    }

    @Override
    public IValidator<T> setNext(IValidator<T> next) {
        this.next = next;
        return this.next;
    }
}
