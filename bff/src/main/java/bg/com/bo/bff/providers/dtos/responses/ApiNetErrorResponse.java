package bg.com.bo.bff.providers.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApiNetErrorResponse {
    @JsonProperty("CodigoError")
    private String codigoError;
    @JsonProperty("Datos")
    private Object datos;
    @JsonProperty("Mensaje")
    private String mensaje;

    public ApiNetErrorResponse(String codigoError, Object datos, String mensaje) {
        this.codigoError = codigoError;
        this.datos = datos;
        this.mensaje = mensaje;
    }
}
