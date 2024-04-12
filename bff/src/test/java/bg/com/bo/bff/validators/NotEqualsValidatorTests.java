package bg.com.bo.bff.validators;

import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.validators.generics.NotEqualsValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotEqualsValidatorTests {

    @Test
    void givenValidValueWhenValidateNotEqualsThenNoThrowException() {
        // Arrange
        String value = "abcd";
        String valueComparable = "1234";

        NotEqualsValidator<String> validator = new NotEqualsValidator<>(valueComparable, new HandledException());

        // Act
        validator.validate(value);
    }

    @Test
    void givenInvalidValueWhenValidateNotEqualsThenThrowException() {
        // Arrange
        String value = "abcd";
        String valueComparable = "abcd";

        NotEqualsValidator<String> validator = new NotEqualsValidator<>(valueComparable, new HandledException());

        // Act
        Exception exception = assertThrows(Exception.class, () -> validator.validate(value));

        // Assert
        assertEquals(HandledException.class, exception.getClass());
    }
}
