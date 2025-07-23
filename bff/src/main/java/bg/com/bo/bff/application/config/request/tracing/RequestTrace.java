package bg.com.bo.bff.application.config.request.tracing;

import com.fasterxml.jackson.annotation.*;

import java.time.ZonedDateTime;
import java.util.Date;

@lombok.Builder
@lombok.Getter
@lombok.Setter
@lombok.ToString
@JsonTypeName(value = "requestTrace")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonInclude
public class RequestTrace {
    private String traceId;
    private String method;
    private String path;
    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS Z")
    private ZonedDateTime in;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS Z")
    private ZonedDateTime out;
    private String headersRequest;
    private String headersResponse;
    private String bodyRequest;
    private String bodyResponse;
    private String userData;
    private long elapsed;
}