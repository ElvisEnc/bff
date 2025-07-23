package bg.com.bo.bff.validators;

import bg.com.bo.bff.commons.annotations.Numeric;
import bg.com.bo.bff.commons.validators.generics.NumericValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NumericValidatorTest {

    private Numeric annotation;
    private ConstraintValidatorContext context;
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;
    private final String failedMessage = "FAILED";
    private NumericValidator validator;

    @BeforeEach
    void setUp() {
        annotation = mock(Numeric.class);
        context = mock(ConstraintValidatorContext.class);
        builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);

        validator = new NumericValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.0", "1", "1.12", "-1", "-1.2"})
    void givenValidNumericValueWhenValidatingBigDecimalThenReturnFalse(String value) {
        // Arrange
        Class<? extends Number> numericType = BigDecimal.class;
        doReturn(numericType).when(annotation).numericType();
        validator.initialize(annotation);

        // Act
        boolean result = validator.isValid(value, context);

        // Assert
        assertTrue(result);
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void givenNullValueWhenValidatingThenReturnFalse(String value) {
        // Arrange
        when(annotation.notNullMessage()).thenReturn(failedMessage);
        when(context.buildConstraintViolationWithTemplate(failedMessage)).thenReturn(builder);
        when(builder.addConstraintViolation()).thenReturn(context);
        validator.initialize(annotation);

        // Act
        boolean result = validator.isValid(value, context);

        // Assert
        assertFalse(result);
        verify(context, times(1)).buildConstraintViolationWithTemplate(failedMessage);
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"field"})
    void givenNotNumericValueWhenValidatingBigDecimalThenReturnFalse(String fieldName) {
        // Arrange
        Class<? extends Number> numericType = BigDecimal.class;
        doReturn(numericType).when(annotation).numericType();

        when(annotation.fieldName()).thenReturn(fieldName);
        when(annotation.onlyNumberMessage()).thenReturn(failedMessage);
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
        when(builder.addConstraintViolation()).thenReturn(context);
        validator.initialize(annotation);

        // Act
        boolean result = validator.isValid("d", context);

        // Assert
        assertFalse(result);
        verify(context, times(1)).buildConstraintViolationWithTemplate(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"d", "1.", ".1", "1.222", "-1."})
    void givenNotValidNumericValueWhenValidatingBigDecimalThenReturnFalse(String value) {
        // Arrange
        Class<? extends Number> numericType = BigDecimal.class;

        when(annotation.onlyNumberMessage()).thenReturn(failedMessage);
        doReturn(numericType).when(annotation).numericType();
        when(context.buildConstraintViolationWithTemplate(failedMessage)).thenReturn(builder);
        when(builder.addConstraintViolation()).thenReturn(context);
        validator.initialize(annotation);

        // Act
        boolean result = validator.isValid(value, context);

        // Assert
        assertFalse(result);
        verify(context, times(1)).buildConstraintViolationWithTemplate(failedMessage);
    }

    @ParameterizedTest
    @ValueSource(classes = {Integer.class, Float.class, BigInteger.class,
            Byte.class, Double.class, Long.class, Short.class,
            AtomicInteger.class, AtomicLong.class})
    void givenNotValidNumericValueWhenValidatingNotImplementedThenReturnFalse(Class<? extends Number> numericType) {
        // Arrange
        when(annotation.onlyNumberMessage()).thenReturn(failedMessage);
        doReturn(numericType).when(annotation).numericType();
        when(context.buildConstraintViolationWithTemplate(failedMessage)).thenReturn(builder);
        when(builder.addConstraintViolation()).thenReturn(context);
        validator.initialize(annotation);

        // Act
        Exception exception = assertThrows(Exception.class, () ->  validator.isValid("1", context));

        // Assert
        assertEquals(NotImplementedException.class, exception.getClass());
    }

    @Test
    void givenInvalidMinValueWhenValidatingBigDecimalThenReturnFalse() {
        // Arrange
        String  value = "1";
        String  min = "2";
        Class<? extends Number> numericType = BigDecimal.class;

        doReturn(numericType).when(annotation).numericType();
        when(annotation.greaterThanMessage()).thenReturn(failedMessage);
        when(annotation.min()).thenReturn(min);
        when(context.buildConstraintViolationWithTemplate(failedMessage)).thenReturn(builder);
        when(builder.addConstraintViolation()).thenReturn(context);
        validator.initialize(annotation);

        // Act
        boolean result = validator.isValid(value, context);

        // Assert
        assertFalse(result);
        verify(context, times(1)).buildConstraintViolationWithTemplate(failedMessage);
    }

    @Test
    void givenInvalidMaxValueWhenValidatingBigDecimalThenReturnFalse() {
        // Arrange
        String  value = "3";
        String  max = "2";
        Class<? extends Number> numericType = BigDecimal.class;

        doReturn(numericType).when(annotation).numericType();
        when(annotation.lowerThanMessage()).thenReturn(failedMessage);
        when(annotation.max()).thenReturn(max);
        when(context.buildConstraintViolationWithTemplate(failedMessage)).thenReturn(builder);
        when(builder.addConstraintViolation()).thenReturn(context);
        validator.initialize(annotation);

        // Act
        boolean result = validator.isValid(value, context);

        // Assert
        assertFalse(result);
        verify(context, times(1)).buildConstraintViolationWithTemplate(failedMessage);
    }
}
