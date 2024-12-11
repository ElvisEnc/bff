package bg.com.bo.bff.application.config;

import bg.com.bo.bff.application.config.request.tracing.RequestTraceResolver;
import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionFilter extends OncePerRequestFilter {

    private final RequestTraceResolver requestTraceResolver;

    @Autowired
    public ExceptionFilter(RequestTraceResolver requestTraceResolver) {
        this.requestTraceResolver = requestTraceResolver;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            chain.doFilter(requestWrapper, responseWrapper);
            responseWrapper.copyBodyToResponse();
        } catch (GenericException e) {
            sendResponse(requestWrapper, responseWrapper, e);
        } catch (Exception e) {
            logger.error(e);
            sendResponse(requestWrapper, responseWrapper, DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, IMiddlewareError error) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.instance(error);
        response.setStatus(error.getHttpCode().value());
        response.setContentType("application/json");
        response.getWriter().write(Util.objectToString(errorResponse));

        requestTraceResolver.log(request, response);

        response.copyBodyToResponse();
    }

    private void sendResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, GenericException exception) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.instance(exception);
        response.setStatus(exception.getStatus().value());
        response.setContentType("application/json");
        response.getWriter().write(Util.objectToString(errorResponse));

        requestTraceResolver.log(request, response);

        response.copyBodyToResponse();
    }
}