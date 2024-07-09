package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DebtsConsultationMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.ServiceAffiliationMWRequest;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.*;
import bg.com.bo.bff.providers.interfaces.IPaymentServicesProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.additional.evaluator.DefaultResultByMWErrorEvaluator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class PaymentServicesProvider extends MiddlewareProvider<PaymentServicesMiddlewareError> implements IPaymentServicesProvider {
    private final String baseUrl;
    private static final String URL_FORMAT = "%s%s";

    public PaymentServicesProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.PAYMENT_SERVICES, PaymentServicesMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientPaymentServicesManager());
        this.baseUrl = middlewareConfig.getUrlBase() + ProjectNameMW.PAYMENT_SERVICES.getName();
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
        DefaultResultByMWErrorEvaluator<AffiliatedServiceMWResponse> additionalEvaluator = DefaultResultByMWErrorEvaluator.instance(PaymentServicesMiddlewareError.MDWPSM_005);
        return get(url, HeadersMW.getDefaultHeaders(parameters), AffiliatedServiceMWResponse.class, additionalEvaluator);
    }

    @Override
    public DebtsConsultationMWResponse debtsConsultation(DebtsConsultationMWRequest request, Map<String, String> parameters) throws IOException {
        String url = baseUrl + PaymentServicesMiddlewareServices.GET_DEBTS.getServiceURL();
        ApiDataResponse<DebtsConsultationMWResponse> mwResponse = post(url, HeadersMW.getDefaultHeaders(parameters), request, ApiDataResponse.class);
        return Util.stringToObject(Util.objectToString(mwResponse.getData()), DebtsConsultationMWResponse.class);
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
        DefaultResultByMWErrorEvaluator<ListServicesMWResponse> additionalEvaluator = DefaultResultByMWErrorEvaluator.instance(PaymentServicesMiddlewareError.MDWPSM_007);
        return get(url, HeadersMW.getDefaultHeaders(parameters), ListServicesMWResponse.class, additionalEvaluator);
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
}
