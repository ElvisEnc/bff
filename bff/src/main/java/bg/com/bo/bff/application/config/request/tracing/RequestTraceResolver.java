package bg.com.bo.bff.application.config.request.tracing;

import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.mappings.services.request.trace.IRequestTraceMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@Log4j2
public class RequestTraceResolver {

    private final IRequestTraceMapper requestTraceMapper;

    @Autowired
    public RequestTraceResolver(IRequestTraceMapper requestTraceMapper) {
        this.requestTraceMapper = requestTraceMapper;
    }

    public void log(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) throws IOException {
        Date requestDateIn = (Date) requestWrapper.getAttribute("in");
        ZonedDateTime in = ZonedDateTime.ofInstant(requestDateIn.toInstant(), ZoneId.systemDefault());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!requestWrapper.getInputStream().isFinished())
           requestWrapper.getInputStream().readAllBytes();

        RequestTrace requestTrace = requestTraceMapper.convert(requestWrapper, responseWrapper, in, authentication);

        if (Util.IsDevLogConfigurationFile())
            log.trace(Util.objectToString(requestTrace, true));
        else
            log.trace(requestTrace);
    }
}
