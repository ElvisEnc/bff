package bg.com.bo.bff.validators;

import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.validators.generics.MinLengthValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MinLengthValidatorTests {

    @Test
    void givenValidValueWhenValidateMinLengthThenNoThrowException() {
        // Arrange
        String value = "1234";
        MinLengthValidator validator = new MinLengthValidator(4, new HandledException());

        // Act
        validator.validate(value);
    }

    @Test
    void givenInvalidValueWhenValidateMinLengthThenThrowException() {
        // Arrange
        String value = "abcd";
        MinLengthValidator validator = new MinLengthValidator(5, new HandledException());

        // Act
        Exception exception = assertThrows(Exception.class, () -> validator.validate(value));

        // Assert
        assertEquals(HandledException.class, exception.getClass());
    }
}
