package bg.com.bo.bff.application.config;

import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExceptionFilterTest {

    ExceptionFilter filter;
    HttpServletRequest request;
    HttpServletResponse response;
    FilterChain chain;
    PrintWriter printWriter;

    @BeforeEach
    void setUp() throws IOException {
        filter = new ExceptionFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        printWriter = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    void givenNoExceptionWhenDoFilterThenContinue() throws ServletException, IOException {
        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(chain, times(1)).doFilter(request, response);
    }


    @Test
    void givenGenericExceptionWhenDoFilterThenWriteErrorResponse() throws ServletException, IOException {
        // Arrange
        GenericException exception = new GenericException(DefaultMiddlewareError.NOT_AUTHENTICATED_USER);
        ErrorResponse errorResponse = ErrorResponse.instance(exception);

        doThrow(exception).when(chain).doFilter(any(), any());

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(printWriter).write(Util.objectToString(errorResponse));
    }

    @Test
    void givenExceptionWhenDoFilterThenWriteErrorResponse() throws ServletException, IOException {
        // Arrange
        Exception exception = new RuntimeException();
        GenericException exceptionExpected = new GenericException(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        ErrorResponse errorResponse = ErrorResponse.instance(exceptionExpected);

        doThrow(exception).when(chain).doFilter(any(), any());

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(printWriter).write(Util.objectToString(errorResponse));
    }
}
