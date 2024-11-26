package bg.com.bo.bff.validators;

import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.validators.generics.ContainsLetterValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContainsLetterValidatorTests {

    @Test
    void givenValidValueWhenValidateContainsLetterThenNoThrowException() {
        // Arrange
        String value = "abcd";
        ContainsLetterValidator validator = new ContainsLetterValidator(new HandledException());

        // Act
        validator.validate(value);
    }

    @Test
    void givenInvalidValueWhenValidateContainsLetterThenThrowException() {
        // Arrange
        String value = "1234";
        ContainsLetterValidator validator = new ContainsLetterValidator(new HandledException());

        // Act
        Exception exception = assertThrows(Exception.class, () -> validator.validate(value));

        // Assert
        assertEquals(HandledException.class, exception.getClass());
    }
}
