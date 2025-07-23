package bg.com.bo.bff.application.config.request.tracing;

import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.mappings.services.request.trace.IRequestTraceMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Component
@Order(3)
@Log4j2
public class RequestTracingFilter extends OncePerRequestFilter {
    private final RequestTraceResolver requestTraceResolver;

    @Autowired
    public RequestTracingFilter(RequestTraceResolver requestTraceResolver) {
        this.requestTraceResolver = requestTraceResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        requestTraceResolver.log(requestWrapper, responseWrapper);

        responseWrapper.copyBodyToResponse();
    }
}
