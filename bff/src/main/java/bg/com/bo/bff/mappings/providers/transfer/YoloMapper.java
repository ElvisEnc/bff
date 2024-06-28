package bg.com.bo.bff.mappings.providers.transfer;

import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.commons.enums.AppDataYoloNet;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.TransferYoloNetRequest;
import bg.com.bo.bff.providers.dtos.response.TransferResponseMD;
import bg.com.bo.bff.providers.dtos.response.ProviderNetResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class YoloMapper implements IYoloMapper {

    public TransferYoloNetRequest mapperRequest(Integer personId, Integer accountId, Integer accountNumber, TransferRequest request) {
        return TransferYoloNetRequest.builder()
                .intCodIdioma(AppDataYoloNet.COD_IDIOMA.getValue())
                .intPersonaRol(AppDataYoloNet.COD_ROL.getValue())
                .intCodAplicacion(AppDataYoloNet.COD_APP.getValue())
                .strTokenSegundoFactor(AppDataYoloNet.SECOND_FACTOR.getValue())
                .intCodPersona(personId)
                .intJtsCuentaOrigen(accountId)
                .strNroCuentaOrigen(accountNumber)
                .intNroCuentaDestino(Integer.valueOf(request.getTargetAccount().getId()))
                .dblImporteTransaccion(request.getAmount().getAmount())
                .intMonedaTran(Util.convertCurrency(request.getAmount().getCurrency()))
                .strDescripcion(request.getData().getDescription())
                .strOrigenUIF(request.getData().getSourceOfFounds() != null ? request.getData().getSourceOfFounds() : "")
                .strDestinoUIF(request.getData().getDestinationOfFounds() != null ? request.getData().getDestinationOfFounds() : "")
                .strMotivoUIF(request.getData().getDestinationOfFounds() != null ? request.getData().getDestinationOfFounds() : "")
                .strGlosaDestino(request.getData().getDescription())
                .build();
    }

    @Override
    public TransferResponseMD convertResponse(ProviderNetResponse yoloNetResponse) {
        TransferResponseMD.TransferMDData transferResponse = TransferResponseMD.TransferMDData.builder().build();
        Object firstElement = yoloNetResponse.getData().get(0);

        Map<String, Object> firstMap = (Map<String, Object>) firstElement;
        transferResponse.setIdReceipt(String.valueOf(firstMap.get("NroTransaccion")));

        Object secondElement = yoloNetResponse.getData().get(1);

        List<Object> secondList = (List<Object>) secondElement;
        for (Object obj : secondList) {
            if (obj instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) obj;
                transferResponse.setAccountingEntry(String.valueOf(map.get("NRO_TRANSFERENCIA")));
                transferResponse.setAccountingDate((String) map.get("FECHATRANSACCION"));
                transferResponse.setAccountingTime((String) map.get("HORATRANSACCION"));
                transferResponse.setAmountDebited(convertToBigDecimal((String) map.get("EQUIVALENTE_MONTO_ORIGEN")));
                transferResponse.setAmountCredited(convertToBigDecimal((String) map.get("EQUIVALENTE_MONTO_DESTINO")));
                transferResponse.setExchangeRateDebit(convertToBigDecimal(((String) map.get("TIPO_CAMBIO_ORIGEN"))));
                transferResponse.setExchangeRateCredit(convertToBigDecimal(((String) map.get("TIPO_CAMBIO"))));
                transferResponse.setAmount(convertToBigDecimal(((String) map.get("IMPORTE_ORIGEN"))));
                String moneda = Objects.equals(map.get("MONEDA_ORIGEN"), "Bs") ? "068" : "840";
                transferResponse.setCurrency(moneda);
                transferResponse.setFromAccountNumber((String) map.get("CUENTA_ORIGEN"));
                transferResponse.setFromHolder((String) map.get("NOMBRE_CLIENTE_ORIGEN"));
                transferResponse.setToAccountNumber((String) map.get("CUENTA_DESTINO"));
                transferResponse.setToHolder((String) map.get("NOMBRE_CLIENTE_DESTINO"));
                transferResponse.setDescription((String) map.get("DESCRIPCION_ORIGEN"));
                transferResponse.setFromCurrency(moneda);
                transferResponse.setToCurrency(moneda);
            }
        }
        return TransferResponseMD.builder()
                .data(transferResponse)
                .build();
    }

    private static BigDecimal convertToBigDecimal(String value) {
        value = value.trim();
        if (value.startsWith(".")) value = "0" + value;
        value = value.replace(",", "");
        return new BigDecimal(value);
    }
}
