package bg.com.bo.bff.application.config.request.tracing;

import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.mappings.interfaces.IRequestTraceMapper;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.*;

@Component
@Order(2)
public class RequestTracingFilter extends OncePerRequestFilter {
    private final IRequestTraceMapper requestTraceMapper;
    private static final Logger logger = LogManager.getLogger(RequestTracingFilter.class.getName());

    @Autowired
    public RequestTracingFilter(IRequestTraceMapper requestTraceMapper) {
        this.requestTraceMapper = requestTraceMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomHeadersRequestWrapper requestWrapper = new CustomHeadersRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        Date in = new Date();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        resolveTraceId(requestWrapper);

        filterChain.doFilter(requestWrapper, responseWrapper);

        RequestTrace requestTrace = requestTraceMapper.convert(requestWrapper, responseWrapper, in,  authentication);

        responseWrapper.copyBodyToResponse();

        logger.trace(requestTrace);
    }

    private static void resolveTraceId(CustomHeadersRequestWrapper requestWrapper) {
        Map<String, String> requestHeaders = Headers.getHeaders(requestWrapper);
        if (!requestHeaders.containsKey(HeadersMW.REQUEST_ID.getName())) {
            String traceId = UUID.randomUUID().toString();
            requestWrapper.addHeader(HeadersMW.REQUEST_ID.getName(), traceId);
        }
    }
}
