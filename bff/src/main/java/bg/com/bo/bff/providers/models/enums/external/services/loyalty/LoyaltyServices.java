package bg.com.bo.bff.providers.models.enums.external.services.loyalty;

import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTermsConditionsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoyaltyServices {

    GET_SYSTEM_CODE("/lealtad/campana/api/v1/personas-ganamovil/codigo-sistema/%s"),
    GET_SUM_POINT("/lealtad/campana/api/v1/acumulacion-puntos-ganamovil/obtener-sumatoria-puntos/%s/campana/1"),
    POST_REGISTER_SUBSCRIPTION("/lealtad/campana/api/v1/suscripciones-ganamovil/contrato-suscripcion"),
    POST_REGISTER_REDEEM_VOUCHER("/lealtad/campana/api/v1/canje-vales-ganamovil/canje-vale"),
    GET_LEVEL("/lealtad/campana/api/v1/suscripciones-ganamovil/obtener-nivel/%s/campana/1"),
    GET_INITIAL_POINTS_VAMOS("/lealtad/campana/api/v1/linkser-ganamovil/obtener-puntos-vamos/%s"),
    GET_POINTS_PERIOD("/lealtad/campana/api/v1/acumulacion-puntos-ganamovil/obtener-sumatoria-puntos-periodo/%s/campana/1"),
    GET_CHECK_SUBSCRIPTION("/lealtad/campana/api/v1/suscripciones-ganamovil/verificar-campanas-suscripcion/%s/campana/1"),
    GET_STATEMENT_POINTS("/lealtad/campana/api/v1/extracto-ganamovil/reporte-extracto"),
    GET_GENERAL_INFORMATION("/lealtad/campana/api/v1/suscripciones-ganamovil/informacion-general/%s"),
    GET_IMAGE("/lealtad/archivos/api/v1/imagenes/buscarImagen/%s/completa/true"),
    GET_LIST_IMAGES("/lealtad/archivos/api/v1/imagenes/buscar-imagenes"),
    GET_CATEGORY_PROMOTION("/lealtad/administracion/api/v1/promociones-ganamovil"),
    GET_CATEGORY_POINTS_LEVEL("/lealtad/administracion/api/v1/niveles-ganamovil/campanas/1"),
    GET_TERMS_CONDITIONS("/lealtad/administracion/api/v1/campanas-ganamovil/obtener-contrato-persona"),
    VALIDATE_PROGRAM("/lealtad/administracion/api/v1/reglas-ganamovil/validar-vamos/%s"),
    GET_PROMOTION("/lealtad/administracion/api/v1/promociones-ganamovil/%s"),
    ;
    private final String serviceURL;
}
