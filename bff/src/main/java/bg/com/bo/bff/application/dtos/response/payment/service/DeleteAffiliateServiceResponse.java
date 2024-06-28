package bg.com.bo.bff.application.dtos.response.payment.services;

import bg.com.bo.bff.commons.enums.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeleteAffiliateServiceResponse implements IGenericControllerResponse {
    SUCCESS("SUCCESS","El servicio se elimino correctamente.");

    private final String code;
    private final String message;
}
