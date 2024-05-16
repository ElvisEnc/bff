package bg.com.bo.bff.providers.dtos.requests;

import java.math.BigDecimal;

public class TransferYoloNetRequestFixture {
    public static TransferYoloNetRequest withDefault() {
        return TransferYoloNetRequest.builder()
                .intCodIdioma("123")
                .intPersonaRol("123")
                .intCodAplicacion("123")
                .strTokenSegundoFactor("123")
                .intCodPersona(123)
                .intJtsCuentaOrigen(123)
                .strNroCuentaOrigen(123)
                .intNroCuentaDestino(123)
                .dblImporteTransaccion(BigDecimal.valueOf(123.20))
                .intMonedaTran("0")
                .strDescripcion("123")
                .strOrigenUIF("123")
                .strDestinoUIF("123")
                .strMotivoUIF("123")
                .strGlosaDestino("123")
                .build();
    }
}