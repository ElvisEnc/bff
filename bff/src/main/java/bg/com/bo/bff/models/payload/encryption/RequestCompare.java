package bg.com.bo.bff.models.payload.encryption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

@AllArgsConstructor
@Getter
public class RequestCompare {
    private HttpMethod method;
    private String path;
}
