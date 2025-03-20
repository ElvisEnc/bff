package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DebtsConsultationMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.ValidateAffiliateCriteriaMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.PaymentDebtsMWRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.ServiceAffiliationMWRequest;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.*;
import bg.com.bo.bff.providers.interfaces.IPaymentServicesProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class PaymentServicesProvider extends MiddlewareProvider<PaymentServicesMiddlewareError> implements IPaymentServicesProvider {
    private final String baseUrl;
    private final String baseUrl2;
    private static final String URL_FORMAT = "%s%s";

    public PaymentServicesProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.PAYMENT_SERVICES, PaymentServicesMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientPaymentServicesManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.PAYMENT_SERVICES.getName();
        baseUrl2 = "http://172.1.16.155:8069/payment-services-manager";
    }

    @Override
    public CategoryMWResponse getCategories(Map<String, String> parameters) throws IOException {
        String url = baseUrl + PaymentServicesMiddlewareServices.GET_CATEGORIES.getServiceURL();
        return get(url, HeadersMW.getDefaultHeaders(parameters), CategoryMWResponse.class);
    }

    @Override
    public SubcategoriesMWResponse getSubcategories(Integer categoryId, Map<String, String> parameters) throws IOException {
        final String pathSubcategories = String.format(PaymentServicesMiddlewareServices.GET_SUBCATEGORIES.getServiceURL(), categoryId);
        final String url = String.format(URL_FORMAT, baseUrl, pathSubcategories);
        return get(url, HeadersMW.getDefaultHeaders(parameters), SubcategoriesMWResponse.class);
    }

    @Override
    public SubCategoryCitiesMWResponse getSubcategoryCities(Integer subCategoryId, Map<String, String> parameters) throws IOException {
        final String pathSubcategories = String.format(PaymentServicesMiddlewareServices.GET_SUBCATEGORY_CITIES.getServiceURL(), subCategoryId);
        final String url = String.format(URL_FORMAT, baseUrl, pathSubcategories);
        return get(url, HeadersMW.getDefaultHeaders(parameters), SubCategoryCitiesMWResponse.class);
    }

    @Override
    public AffiliatedServiceMWResponse getAffiliatedServices(Integer personId, Map<String, String> parameters) throws IOException {
        String url = baseUrl + String.format(PaymentServicesMiddlewareServices.GET_AFFILIATIONS_SERVICES.getServiceURL(), personId);
        ByMwErrorResponseHandler<AffiliatedServiceMWResponse> responseHandler = ByMwErrorResponseHandler.instance(PaymentServicesMiddlewareError.MDWPSM_005);
        return get(url, HeadersMW.getDefaultHeaders(parameters), AffiliatedServiceMWResponse.class, responseHandler);
    }

    @Override
    public DebtsConsultationMWResponse debtsConsultation(DebtsConsultationMWRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl2 + PaymentServicesMiddlewareServices.DEBTS.getServiceURL();
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, DebtsConsultationMWResponse.class);
    }

    @Override
    public PaymentDebtsMWResponse paymentDebts(PaymentDebtsMWRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + PaymentServicesMiddlewareServices.POST_PAYMENTS_DEBTS.getServiceURL();
        ApiDataResponse<PaymentDebtsMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(parameters), request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), PaymentDebtsMWResponse.class);
    }

    @Override
    public ServiceAffiliationMWResponse serviceAffiliation(ServiceAffiliationMWRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + PaymentServicesMiddlewareServices.POST_SERVICE_AFFILIATION.getServiceURL();
        ApiDataResponse<ServiceAffiliationMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(parameters), request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), ServiceAffiliationMWResponse.class);
    }

    @Override
    public ListServicesMWResponse getServicesByCategoryAndCity(Integer subCategoryId, Integer cityId, Map<String, String> parameters) throws IOException {
        final String pathServices = String.format(PaymentServicesMiddlewareServices.GET_SERVICES.getServiceURL(), subCategoryId, cityId);
        final String url = String.format(URL_FORMAT, baseUrl, pathServices);
        ByMwErrorResponseHandler<ListServicesMWResponse> responseHandler = ByMwErrorResponseHandler.instance(PaymentServicesMiddlewareError.MDWPSM_007);
        return get(url, HeadersMW.getDefaultHeaders(parameters), ListServicesMWResponse.class, responseHandler);
    }

    @Override
    public DeleteAffiliateServiceMWResponse deleteAffiliationService(DeleteAffiliateServiceMWRequest request, Map<String, String> parameters) throws IOException {
        final String url = String.format(URL_FORMAT, baseUrl, PaymentServicesMiddlewareServices.DELETE_AFFILIATE_SERVICE.getServiceURL());
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, DeleteAffiliateServiceMWResponse.class);
    }

    @Override
    public AffiliateCriteriaMWResponse getAffiliateCriteria(String personId, String serviceCode, Map<String, String> parameters) throws IOException {
        final String pathServices = String.format(PaymentServicesMiddlewareServices.GET_AFFILIATE_CRITERIA.getServiceURL(), serviceCode);
        final String url = String.format(URL_FORMAT, baseUrl, pathServices);
        return get(url, HeadersMW.getDefaultHeaders(parameters), AffiliateCriteriaMWResponse.class);
    }

    @Override
    public ValidateAffiliateCriteriaMWResponse validateAffiliateCriteria(ValidateAffiliateCriteriaMWRequest mwRequest, Map<String, String> parameters) throws IOException {
        final String pathServices = String.format(PaymentServicesMiddlewareServices.VALIDATE_AFFILIATE_CRITERIA.getServiceURL());
        final String url = String.format(URL_FORMAT, baseUrl, pathServices);
        ApiDataResponse<ValidateAffiliateCriteriaMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(parameters), mwRequest, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), ValidateAffiliateCriteriaMWResponse.class);
    }

    @Override
    public ListServicesMWResponse getListService(Map<String, String> parameter) throws IOException {
        String url = baseUrl + PaymentServicesMiddlewareServices.GET_LIST_SERVICES.getServiceURL();
        ByMwErrorResponseHandler<ListServicesMWResponse> responseHandler = ByMwErrorResponseHandler.instance(PaymentServicesMiddlewareError.MDWPSM_007);
        return get(url, HeadersMW.getDefaultHeaders(parameter), ListServicesMWResponse.class, responseHandler);
    }
}
