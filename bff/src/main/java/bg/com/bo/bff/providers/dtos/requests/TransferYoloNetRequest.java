package bg.com.bo.bff.providers.dtos.requests;

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
    private String intCodIdioma;           // 1,
    private String intPersonaRol;          // 32992,
    private String intCodAplicacion;       // 20
    private String strTokenSegundoFactor;   // "0",
    private Integer intCodPersona;          // 1086139,
    private Integer intJtsCuentaOrigen;     // 1943770,
    private Integer strNroCuentaOrigen;      //  "1310104470",
    private Integer intNroCuentaDestino;    // 70292838,
    private BigDecimal dblImporteTransaccion;  // 1478,
    private String intMonedaTran;          // 0.5,
    private String strDescripcion;          // "Transferencia billetera",
    private String strOrigenUIF;            // "",
    private String strDestinoUIF;           // "",
    private String strMotivoUIF;            // "",
    private String strGlosaDestino;         // "Transferencia billetera",
}
