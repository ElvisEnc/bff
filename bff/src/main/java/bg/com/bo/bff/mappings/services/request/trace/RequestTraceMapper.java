package bg.com.bo.bff.mappings.services.request.trace;

import bg.com.bo.bff.application.config.request.tracing.RequestTrace;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.commons.utils.UtilAuthentication;
import bg.com.bo.bff.models.UserData;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class RequestTraceMapper implements IRequestTraceMapper {
    private static final Logger logger = LogManager.getLogger(RequestTraceMapper.class.getName());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public RequestTrace convert(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, ZonedDateTime in, Authentication authentication) {
        try {
            ZonedDateTime out = ZonedDateTime.now();
            UserData currentUser = UtilAuthentication.getUserData(authentication);
            Map<String, String> requestHeaders = Headers.getHeaders(requestWrapper);
            String traceId = getTraceId(requestHeaders);
            String requestBody = getRequestBody(requestWrapper);
            String payload = getResponseBody(responseWrapper);
            Map<String, String> responseHeaders = Headers.getHeaders(responseWrapper);
            long elapsed = getElapsed(in, out);

            return RequestTrace.builder()
                    .traceId(traceId)
                    .path(requestWrapper.getRequestURI())
                    .method(requestWrapper.getMethod())
                    .in(in)
                    .out(out)
                    .elapsed(elapsed)
                    .headersResponse(objectToString(responseHeaders))
                    .bodyResponse(payload)
                    .status(responseWrapper.getStatus())
                    .userData(currentUser != null ? objectToString(currentUser) : null)
                    .headersRequest(objectToString(requestHeaders))
                    .bodyRequest(requestBody)
                    .build();
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    private static String getTraceId(Map<String, String> requestHeaders) {
        if (requestHeaders.containsKey(HeadersMW.REQUEST_ID.getName()))
            return requestHeaders.get(HeadersMW.REQUEST_ID.getName());
        return null;
    }

    private static long getElapsed(ZonedDateTime in, ZonedDateTime out) {
        return Duration.between(in, out).toMillis();
    }

    private static String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        return new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
    }

    private static String getResponseBody(ContentCachingResponseWrapper responseWrapper) throws UnsupportedEncodingException {
        byte[] responseBody = responseWrapper.getContentAsByteArray();
        return new String(responseBody, responseWrapper.getCharacterEncoding());
    }

    private static <T> String objectToString(T object) throws IOException {
        if (object == null)
            return null;
        return objectMapper.writeValueAsString(object);
    }
}
