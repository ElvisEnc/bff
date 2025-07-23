package bg.com.bo.bff.providers.dtos.response.generic;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApiErrorResponse {
    @JsonIgnore
    private HttpStatus status;
    private Integer code;
    private String errorType;
    private List<ErrorDetailResponse> errorDetailResponse;
    private String mensaje;
    private Integer codigoEstado;

    public ApiErrorResponse(HttpStatus status, String errorType) {
        this.status = status;
        this.code = status.value();
        this.errorType = errorType;
    }

    public ApiErrorResponse(
            HttpStatus status,
            String errorType,
            List<ErrorDetailResponse> detail,
            String mensaje
            ) {
        this.status = status;
        this.code = status.value();
        this.errorType = errorType;
        this.errorDetailResponse = detail;
        this.mensaje = mensaje;
    }
}
