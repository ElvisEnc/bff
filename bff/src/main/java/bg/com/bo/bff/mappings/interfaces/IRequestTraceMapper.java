package bg.com.bo.bff.mappings.interfaces;

import bg.com.bo.bff.application.config.request.tracing.RequestTrace;
import org.springframework.security.core.Authentication;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.time.Instant;
import java.util.Date;

public interface IRequestTraceMapper {
    RequestTrace convert(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, Date in, Authentication authentication);
}
