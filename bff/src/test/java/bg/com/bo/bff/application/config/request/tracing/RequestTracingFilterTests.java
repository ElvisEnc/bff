package bg.com.bo.bff.application.config.request.tracing;

import bg.com.bo.bff.mappings.services.request.trace.RequestTraceMapper;
import bg.com.bo.bff.mappings.services.request.trace.IRequestTraceMapper;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestTracingFilterTests {

    @Test
    void givenRequestTracingFilterWhenExecuteRequestThenTraceAndAddTraceIdHeader() throws ServletException, IOException {
        // Arrange
        RequestTrace requestTrace = RequestTrace.builder()
                .traceId(UUID.randomUUID().toString())
                .build();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        IRequestTraceMapper requestTraceMapper = mock(IRequestTraceMapper.class);
        when(requestTraceMapper.convert(any(), any(), any(), any())).thenReturn(requestTrace);

        ArgumentCaptor<RequestTrace> captor = ArgumentCaptor.forClass(RequestTrace.class);
        Logger logger = mock(Logger.class);

        try (MockedStatic<LogManager> mocked = mockStatic(LogManager.class)) {
            mocked.when(() -> LogManager.getLogger(RequestTracingFilter.class)).thenReturn(logger);

            RequestTracingFilter filter = new RequestTracingFilter(requestTraceMapper);

            //Act
            filter.doFilter(req, res, chain);
        }

        //Assert
        verify(logger, times(1)).trace(captor.capture());
        RequestTrace result = captor.getValue();
        assertNotNull(result);
        assertTrue(result.getTraceId() != null && !result.getTraceId().isEmpty());
    }
}
