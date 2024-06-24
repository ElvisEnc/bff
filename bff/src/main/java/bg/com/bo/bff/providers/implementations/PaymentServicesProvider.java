package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.SubCategoryCitiesMWResponse;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.response.payment.services.SubcategoriesMWResponse;
import bg.com.bo.bff.providers.interfaces.IPaymentServicesProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class PaymentServicesProvider  extends MiddlewareProvider<PaymentServicesMiddlewareError>  implements IPaymentServicesProvider {
    public PaymentServicesProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.PAYMENT_SERVICES, PaymentServicesMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientPaymentServicesManager());
    }

    @Override
    public SubcategoriesMWResponse getSubcategories(Integer categoryId, Map<String, String> parameters) throws IOException {
        final String pathSubcategories = String.format(PaymentServicesMiddlewareServices.GET_SUBCATEGORIES.getServiceURL(),categoryId);
        final String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.PAYMENT_SERVICES.getName(), pathSubcategories);
        final Header[] headers = setHeaders(parameters);
        return get(url,headers,SubcategoriesMWResponse.class);
    }

    @Override
    public SubCategoryCitiesMWResponse getSubcategoryCities(Integer subCategoryId, Map<String, String> parameters) throws IOException {

        final String pathSubcategories = String.format(PaymentServicesMiddlewareServices.GET_SUBCATEGORY_CITIES.getServiceURL(),subCategoryId);
        final String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.PAYMENT_SERVICES.getName(), pathSubcategories);
        final Header[] headers = setHeaders(parameters);
        return get(url,headers,SubCategoryCitiesMWResponse.class);

    }

    private static Header[] setHeaders(Map<String, String> parameters) {
        return new Header[]{
                new BasicHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName()),
                new BasicHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode())),
                new BasicHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode())),
                new BasicHeader(DeviceMW.DEVICE_NAME.getCode(), parameters.get(DeviceMW.DEVICE_NAME.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_X.getCode(), parameters.get(DeviceMW.GEO_POSITION_X.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_Y.getCode(), parameters.get(DeviceMW.GEO_POSITION_Y.getCode())),
                new BasicHeader(DeviceMW.APP_VERSION.getCode(), parameters.get(DeviceMW.APP_VERSION.getCode())),
        };
    }
}
