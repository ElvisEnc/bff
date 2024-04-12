package bg.com.bo.bff.commons.validators.generics;

public interface IValidator<T> {
    IValidator<T> setNext(IValidator<T> next);

    void validate(T value);
}
