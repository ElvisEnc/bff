package bg.com.bo.bff.providers.models.enums.external.services.loyalty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoyaltyServices {

    GET_SYSTEM_CODE("/lealtad/campana/api/v1/personas-ganamovil/codigo-sistema/%s"),
    GET_SUM_POINT("/lealtad/campana/api/v1/acumulacion-puntos-ganamovil/obtener-sumatoria-puntos/%s/campana/1"),
    POST_REGISTER_SUBSCRIPTION("/lealtad/campana/api/v1/suscripciones-ganamovil/contrato-suscripcion")
    ;

    private final String serviceURL;
}
