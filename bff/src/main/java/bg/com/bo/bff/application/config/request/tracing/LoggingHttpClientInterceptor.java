package bg.com.bo.bff.application.config.request.tracing;

import bg.com.bo.bff.commons.enums.EnvProfile;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.commons.utils.UtilAuthentication;
import bg.com.bo.bff.models.UserData;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.*;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class LoggingHttpClientInterceptor implements HttpRequestInterceptor, HttpResponseInterceptor {
    private static final ThreadLocal<RequestTrace> requestTraceHolder = new ThreadLocal<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(LoggingHttpClientInterceptor.class.getName());
    private final HttpServletRequest httpServletRequest;
    private final Environment env;

    public LoggingHttpClientInterceptor(HttpServletRequest httpServletRequest, Environment env) {
        this.httpServletRequest = httpServletRequest;
        this.env = env;
    }

    @Override
    public void process(HttpRequest request, HttpContext context) throws IOException {
        String method = request.getRequestLine().getMethod();
        String path = request.getRequestLine().getUri();
        ZonedDateTime requestTime = ZonedDateTime.now();
        String bodyRequest = extractRequestBody(request);
        UserData userData = UtilAuthentication.getUserData(SecurityContextHolder.getContext().getAuthentication());
        String traceId = httpServletRequest.getHeader(HeadersMW.REQUEST_ID.getName());

        RequestTrace trace = RequestTrace.builder()
                .traceId(traceId)
                .method(method)
                .path(path)
                .in(requestTime)
                .headersRequest(headersToString(request.getAllHeaders()))
                .bodyRequest(bodyRequest)
                .userData(objectMapper.writeValueAsString(userData))
                .build();

        requestTraceHolder.set(trace);
    }

    @Override
    public void process(HttpResponse response, HttpContext context) throws IOException {
        ZonedDateTime responseTime = ZonedDateTime.now();
        RequestTrace trace = requestTraceHolder.get();

        if (trace != null) {
            String bodyResponse = extractResponseBody(response);
            trace.setOut(responseTime);
            trace.setStatus(response.getStatusLine().getStatusCode());
            trace.setHeadersResponse(headersToString(response.getAllHeaders()));
            trace.setBodyResponse(bodyResponse);
            trace.setElapsed(Duration.between(responseTime, trace.getIn()).toMillis());
            if (Arrays.stream(env.getActiveProfiles()).toList().contains(EnvProfile.dev.name()))
                logger.trace(Util.objectToString(trace, true));
            else
                logger.trace(trace);
        }
        requestTraceHolder.remove();
    }

    private String extractRequestBody(HttpRequest request) throws IOException {
        if (request instanceof HttpEntityEnclosingRequest entityRequest) {
            if (entityRequest.getEntity() != null) {
                return EntityUtils.toString(entityRequest.getEntity());
            }
        }
        return null;
    }

    private String extractResponseBody(HttpResponse response) throws IOException {
        if (response.getEntity() != null) {
            HttpEntity originalEntity = response.getEntity();
            BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(originalEntity);
            response.setEntity(bufferedEntity);
            return EntityUtils.toString(bufferedEntity);
        }
        return null;
    }

    private String headersToString(Header[] headers) {
        Map<String, String> headersMap = Arrays.stream(headers)
                .collect(Collectors.toMap(
                        Header::getName,
                        Header::getValue,
                        (existingValue, newValue) -> existingValue + ", " + newValue
                ));
        try {
            return objectMapper.writeValueAsString(headersMap);
        } catch (JsonProcessingException e) {
            logger.error("Error serializing headers to JSON", e);
            return "{}";
        }
    }
}
