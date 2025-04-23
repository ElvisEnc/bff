package bg.com.bo.bff.providers.dtos.response.generic;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class ExternalResponse {
    private Integer codigoEstado;
    private String mensaje;

    public ExternalResponse() {

    }

    public ExternalResponse(Integer codigoEstado, String mensaje) {
        this.codigoEstado = codigoEstado;
        this.mensaje = mensaje;
    }

    public Integer getCodigoEstado() {
        return codigoEstado;
    }

    public String getMensaje() {
        return mensaje;
    }
}
