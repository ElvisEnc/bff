package bg.com.bo.bff.providers.models.enums.external.services.loyalty;

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
    GET_STORE_FEATURED("/lealtad/beneficios/api/v1/comercios-ganamovil/comercios-destacados"),
    GET_QR_TRANSACTION("/lealtad/beneficios/api/v1/vales-ganamovil/%s/tipo-vale/%s"),
    GET_VOUCHER_TRANSACTION("/lealtad/beneficios/api/v1/vales-ganamovil/campana/1/persona/%s/estado/%s"),
    GET_TRADE_CATEGORIES("/lealtad/beneficios/api/v1/comercios-ganamovil/categorias-comercios"),
    GET_FEATURED_MERCHANTS("/lealtad/beneficios/api/v1/comercios-ganamovil/comercios-destacados"),
    GET_CITIES("/lealtad/beneficios/api/v1/ciudades-ganamovil"),
    GET_CITY_CATEGORY_MERCHANTILS("/lealtad/beneficios/api/v1/comercios-ganamovil/categoria-comercio/ciudad"),
    GET_QR_VOUCHER_TRANSACTION("/lealtad/beneficios/api/v1/vales-ganamovil/%s/tipo-vale/%s"),
    POST_COMPANY_MERCHANT_CATEGORY_VOUCHERS("/lealtad/beneficios/api/v1/beneficios-ganamovil/obtener-todos"),
    GET_VOUCHER_TRANSACTED_LIST("/lealtad/beneficios/api/v1/vales-ganamovil/campana/1/persona/%s/estado/%s");

    private final String serviceURL;
}
