package bg.com.bo.bff.validators;

import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.validators.generics.ContainsDigitValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContainsDigitValidatorTests {

    @Test
    void givenValidValueWhenValidateContainsDigitThenNoThrowException() {
        // Arrange
        String value = "1234";
        ContainsDigitValidator validator = new ContainsDigitValidator(new HandledException());

        // Act
        validator.validate(value);
    }

    @Test
    void givenInvalidValueWhenValidateContainsDigitThenThrowException() {
        // Arrange
        String value = "abcd";
        ContainsDigitValidator validator = new ContainsDigitValidator(new HandledException());

        // Act
        Exception exception = assertThrows(Exception.class, () -> validator.validate(value));

        // Assert
        assertEquals(HandledException.class, exception.getClass());
    }
}
