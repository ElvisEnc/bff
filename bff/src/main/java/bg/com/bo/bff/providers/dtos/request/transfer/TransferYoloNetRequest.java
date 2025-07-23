package bg.com.bo.bff.providers.dtos.request.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferYoloNetRequest {
    private String intCodIdioma;
    private String intPersonaRol;
    private String intCodAplicacion;
    private String strTokenSegundoFactor;
    private Integer intCodPersona;
    private Integer intJtsCuentaOrigen;
    private Integer strNroCuentaOrigen;
    private Integer intNroCuentaDestino;
    private BigDecimal dblImporteTransaccion;
    private String intMonedaTran;
    private String strDescripcion;
    private String strOrigenUIF;
    private String strDestinoUIF;
    private String strMotivoUIF;
    private String strGlosaDestino;
}
