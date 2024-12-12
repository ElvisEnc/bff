package bg.com.bo.bff.application.config.request.tracing;

import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Log4j2
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class TraceIdHeaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request.setAttribute("in", new Date());
        request.setAttribute("encrypted", false);

        CustomHeadersRequestWrapper requestWrapper = new CustomHeadersRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        String traceId = resolveRequestTraceId(requestWrapper);
        traceIdToResponse(responseWrapper, traceId);

        filterChain.doFilter(requestWrapper, response);

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

    private static void traceIdToResponse(ContentCachingResponseWrapper responseWrapper, String traceId) {
        Map<String, String> requestHeaders = Headers.getHeaders(responseWrapper);
        if (!requestHeaders.containsKey(HeadersMW.REQUEST_ID.getName()))
            responseWrapper.addHeader(HeadersMW.REQUEST_ID.getName(), traceId);
        else
            responseWrapper.setHeader(HeadersMW.REQUEST_ID.getName(), traceId);
    }
}
