package bg.com.bo.bff.provider.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.http.HttpStatus;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorResponse {
    @JsonIgnore
    private HttpStatus status;
    private Integer code;
    private String errorType;
    private Set<ErrorDetailResponse> errorDetail;

    public ApiErrorResponse(HttpStatus status, String errorType) {
        this.status = status;
        this.code = status.value();
        this.errorType = errorType;
    }

    public ApiErrorResponse(
            HttpStatus status,
            String errorType,
            Set<ErrorDetailResponse> detail) {
        this.status = status;
        this.code = status.value();
        this.errorType = errorType;
        this.errorDetail = detail;
    }
}
