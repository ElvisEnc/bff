package bg.com.bo.bff.providers.dtos.response.generic;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@Builder
@ToString
public class DynamicAppError {
    private HttpStatus status;
    private String providerCode;
    private String message;

    public DynamicAppError(HttpStatus status, String providerCode, String message) {
        this.status = status;
        this.providerCode = providerCode;
        this.message = message;
    }
}
