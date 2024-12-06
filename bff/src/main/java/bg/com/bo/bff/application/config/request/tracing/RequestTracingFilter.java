package bg.com.bo.bff.application.config.request.tracing;

import bg.com.bo.bff.commons.enums.EnvProfile;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.mappings.services.request.trace.IRequestTraceMapper;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Component
@Order(4)
public class RequestTracingFilter extends OncePerRequestFilter {
    private final IRequestTraceMapper requestTraceMapper;
    private static final Logger logger = LogManager.getLogger(RequestTracingFilter.class.getName());
    private final Environment env;

    @Autowired
    public RequestTracingFilter(IRequestTraceMapper requestTraceMapper, Environment env) {
        this.requestTraceMapper = requestTraceMapper;
        this.env = env;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomHeadersRequestWrapper requestWrapper = new CustomHeadersRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        ZonedDateTime in = ZonedDateTime.now();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String traceId = resolveRequestTraceId(requestWrapper);

        filterChain.doFilter(requestWrapper, responseWrapper);

        RequestTrace requestTrace = requestTraceMapper.convert(requestWrapper, responseWrapper, in, authentication);

        resolverResponseTraceId(responseWrapper, traceId);
        responseWrapper.copyBodyToResponse();

        if (Arrays.stream(env.getActiveProfiles()).toList().contains(EnvProfile.dev.name()))
            logger.trace(Util.objectToString(requestTrace, true));
        else
            logger.trace(requestTrace);
    }

    private static String resolveRequestTraceId(CustomHeadersRequestWrapper requestWrapper) {
        String traceId;
        Map<String, String> requestHeaders = Headers.getHeaders(requestWrapper);
        if (requestHeaders.containsKey(HeadersMW.KONG_REQUEST_ID.getName().toLowerCase()))
            traceId = requestWrapper.getHeader(HeadersMW.KONG_REQUEST_ID.getName());
        else
            traceId = UUID.randomUUID().toString();

        if (!requestHeaders.containsKey(HeadersMW.REQUEST_ID.getName()))
            requestWrapper.addHeader(HeadersMW.REQUEST_ID.getName(), traceId);

        return traceId;
    }

    private static void resolverResponseTraceId(ContentCachingResponseWrapper responseWrapper, String traceId) {
        Map<String, String> requestHeaders = Headers.getHeaders(responseWrapper);
        if (!requestHeaders.containsKey(HeadersMW.REQUEST_ID.getName()))
            responseWrapper.addHeader(HeadersMW.REQUEST_ID.getName(), traceId);
        else
            responseWrapper.setHeader(HeadersMW.REQUEST_ID.getName(), traceId);
    }
}
