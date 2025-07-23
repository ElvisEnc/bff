package bg.com.bo.bff.application.config;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.time.Instant;
import org.apache.logging.log4j.message.SimpleMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObfuscationRewritePolicyTest {

    private ObfuscationRewritePolicy policy;
    private LogEvent mockEvent;

    @BeforeEach
    void setup() {
        policy = ObfuscationRewritePolicy.createPolicy();

        mockEvent = Mockito.mock(LogEvent.class);
        Instant mockInstant = Mockito.mock(Instant.class);
        Mockito.when(mockEvent.getInstant()).thenReturn(mockInstant);
        java.time.Instant referenceInstant = java.time.Instant.now();
        Mockito.when(mockInstant.getEpochSecond()).thenReturn(referenceInstant.getEpochSecond());
        Mockito.when(mockInstant.getNanoOfSecond()).thenReturn(referenceInstant.getNano());
    }

    @ParameterizedTest
    @MethodSource("provideMessagesForRewriteTest")
    void givenDataWhenRewriteThenObfuscateWhenAppropriate(String originalMessage, String expectedMessage) {
        // Arrange
        Mockito.when(mockEvent.getMessage()).thenReturn(new SimpleMessage(originalMessage));

        // Act
        LogEvent rewrittenEvent = policy.rewrite(mockEvent);

        // Assert
        assertEquals(expectedMessage, rewrittenEvent.getMessage().getFormattedMessage());
    }

    private static List<Object[]> provideMessagesForRewriteTest() {
        return List.of(
                new Object[]{"{\\\"password\\\":\\\"secret123\\\",\\\"tokenFinger\\\":\\\"abc\\\"}", "{\\\"password\\\": \\\"****\\\",\\\"tokenFinger\\\": \\\"****\\\"}"},
                new Object[]{"{\\\"username\\\":\\\"user1\\\",\\\"password\\\":\\\"secret123\\\"}", "{\\\"username\\\":\\\"user1\\\",\\\"password\\\": \\\"****\\\"}"},
                new Object[]{"{\\\"username\\\":\\\"user1\\\",\\\"email\\\":\\\"user1@example.com\\\"}", "{\\\"username\\\":\\\"user1\\\",\\\"email\\\":\\\"user1@example.com\\\"}"}
        );
    }
}
