package bg.com.bo.bff.application.config.request.tracing;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Order(1)
@Log4j2
public class RawRequestTracingFilter extends OncePerRequestFilter {
    private final RequestTraceResolver requestTraceResolver;

    @Autowired
    public RawRequestTracingFilter(RequestTraceResolver requestTraceResolver) {
        this.requestTraceResolver = requestTraceResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        if ((boolean) request.getAttribute("encrypted"))
            requestTraceResolver.log(requestWrapper, responseWrapper);

        responseWrapper.copyBodyToResponse();
    }
}
