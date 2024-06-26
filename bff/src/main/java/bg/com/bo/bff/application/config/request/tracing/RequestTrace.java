package bg.com.bo.bff.application.config.request.tracing;

import bg.com.bo.bff.models.UserData;
import com.fasterxml.jackson.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@lombok.Builder
@lombok.Getter
@lombok.Setter
@lombok.ToString
@JsonTypeName(value = "requestTrace")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class RequestTrace {
    private String traceId;
    private String method;
    private String path;
    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date in;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date out;
    private Map<String, String> headersRequest;
    private Map<String, String> headersResponse;
    private String bodyRequest;
    private String bodyResponse;
    private UserData userData;
    private long elapsed;
}