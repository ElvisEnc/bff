package bg.com.bo.bff.application.config;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.time.Instant;
import org.apache.logging.log4j.message.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ThrowableRewritePolicyTest {

    private ThrowableRewritePolicy policy;
    private LogEvent mockEvent;

    @BeforeEach
    public void setup() {
        mockEvent = Mockito.mock(LogEvent.class);
        Instant mockInstant = Mockito.mock(Instant.class);
        Mockito.when(mockEvent.getInstant()).thenReturn(mockInstant);
        java.time.Instant referenceInstant = java.time.Instant.now();
        Mockito.when(mockInstant.getEpochSecond()).thenReturn(referenceInstant.getEpochSecond());
        Mockito.when(mockInstant.getNanoOfSecond()).thenReturn(referenceInstant.getNano());

        policy = ThrowableRewritePolicy.createPolicy();
    }

    @ParameterizedTest
    @CsvSource({"5, 3", "2, 2"})
    void givenStacktraceWhenRewriteThenTruncatesOrKeepsStackTrace(int initialStackTraceSize, int expectedStackTraceSize) {
        // Arrange
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTrace = new StackTraceElement[initialStackTraceSize];
        for (int i = 0; i < initialStackTraceSize; i++)
            stackTrace[i] = new StackTraceElement("Class" + i, "method" + i, "File" + i, i);
        throwable.setStackTrace(stackTrace);

        Message message = mock(Message.class);
        when(message.getThrowable()).thenReturn(throwable);
        when(mockEvent.getMessage()).thenReturn(message);

        // Act
        LogEvent resultEvent = policy.rewrite(mockEvent);

        // Arrange
        Throwable resultThrowable = resultEvent.getMessage().getThrowable();
        assertNotNull(resultThrowable);
        assertEquals(expectedStackTraceSize, resultThrowable.getStackTrace().length);
    }

    @Test
    void givenNoThrowableWhenRewriteThenReturnSameEvent() {
        // Arrange
        Message message = mock(Message.class);
        when(message.getThrowable()).thenReturn(null);
        when(mockEvent.getMessage()).thenReturn(message);

        // Act
        LogEvent resultEvent = policy.rewrite(mockEvent);

        //Assert
        assertSame(mockEvent, resultEvent);
    }
}
