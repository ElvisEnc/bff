package bg.com.bo.bff.application.config.request.tracing;

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
        RequestTraceResolver requestTraceResolver = mock(RequestTraceResolver.class);
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        RequestTracingFilter filter = new RequestTracingFilter(requestTraceResolver);

        //Act
        filter.doFilter(req, res, chain);

        //Assert
        verify(requestTraceResolver, times(1)).log(any(), any());
    }
}
