package bg.com.bo.bff.application.config;

import bg.com.bo.bff.application.config.request.tracing.RequestTraceResolver;
import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExceptionFilterTest {

    private ExceptionFilter exceptionFilter;

    @Mock
    private RequestTraceResolver requestTraceResolver;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    private ByteArrayOutputStream responseStream;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        exceptionFilter = new ExceptionFilter(requestTraceResolver);

        responseStream = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) {
                responseStream.write(b);
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }
        };

        when(httpServletResponse.getOutputStream()).thenReturn(servletOutputStream);
        when(httpServletResponse.getWriter()).thenReturn(new PrintWriter(responseStream));
    }

    @Test
    void testDoFilterInternal_genericException() throws Exception {
        // Arrange
        doThrow(new GenericException("Test error", HttpStatus.BAD_REQUEST)).when(filterChain)
                .doFilter(any(ContentCachingRequestWrapper.class), any(ContentCachingResponseWrapper.class));

        // Act
        exceptionFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        // Assert
        verify(httpServletResponse).setStatus(HttpStatus.BAD_REQUEST.value());
        String errorResponseJson = responseStream.toString();
        ErrorResponse expectedErrorResponse = ErrorResponse.instance(new GenericException("Test error", HttpStatus.BAD_REQUEST));
        assertEquals(Util.objectToString(expectedErrorResponse), errorResponseJson);
    }

    @Test
    void testDoFilterInternal_unexpectedException() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Unexpected error")).when(filterChain)
                .doFilter(any(ContentCachingRequestWrapper.class), any(ContentCachingResponseWrapper.class));

        // Act
        exceptionFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        // Assert
        verify(httpServletResponse).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String errorResponseJson = responseStream.toString();
        ErrorResponse expectedErrorResponse = ErrorResponse.instance(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        assertEquals(Util.objectToString(expectedErrorResponse), errorResponseJson);
    }
}

