package bg.com.bo.bff.validators;

import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.validators.generics.MaxLengthValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MaxLengthValidatorTests {

    @Test
    void givenValidValueWhenValidateMaxLengthThenNoThrowException() {
        // Arrange
        String value = "1234";
        MaxLengthValidator validator = new MaxLengthValidator(4, new HandledException());

        // Act
        validator.validate(value);
    }

    @Test
    void givenInvalidValueWhenValidateMaxLengthThenThrowException() {
        // Arrange
        String value = "abcde";
        MaxLengthValidator validator = new MaxLengthValidator(4, new HandledException());

        // Act
        Exception exception = assertThrows(Exception.class, () -> validator.validate(value));

        // Assert
        assertEquals(HandledException.class, exception.getClass());
    }
}
