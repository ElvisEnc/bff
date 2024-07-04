package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DeleteAffiliateServiceMWRequest;
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
    public PaymentServicesProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.PAYMENT_SERVICES, PaymentServicesMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientPaymentServicesManager());
    }

    @Override
    public CategoryMWResponse getCategories(Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.PAYMENT_SERVICES.getName() + PaymentServicesMiddlewareServices.GET_CATEGORIES.getServiceURL();
        return get(url, HeadersMW.getDefaultHeaders(parameters), CategoryMWResponse.class);
    }

    @Override
    public SubcategoriesMWResponse getSubcategories(Integer categoryId, Map<String, String> parameters) throws IOException {
        final String pathSubcategories = String.format(PaymentServicesMiddlewareServices.GET_SUBCATEGORIES.getServiceURL(), categoryId);
        final String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.PAYMENT_SERVICES.getName(), pathSubcategories);
        return get(url, HeadersMW.getDefaultHeaders(parameters), SubcategoriesMWResponse.class);
    }

    @Override
    public SubCategoryCitiesMWResponse getSubcategoryCities(Integer subCategoryId, Map<String, String> parameters) throws IOException {
        final String pathSubcategories = String.format(PaymentServicesMiddlewareServices.GET_SUBCATEGORY_CITIES.getServiceURL(), subCategoryId);
        final String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.PAYMENT_SERVICES.getName(), pathSubcategories);
        return get(url, HeadersMW.getDefaultHeaders(parameters), SubCategoryCitiesMWResponse.class);
    }

    @Override
    public AffiliatedServiceMWResponse getAffiliationsServices(Integer personId, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.PAYMENT_SERVICES.getName() + String.format(PaymentServicesMiddlewareServices.GET_AFFILIATIONS_SERVICES.getServiceURL(), personId);
        DefaultResultByMWErrorEvaluator<AffiliatedServiceMWResponse> additionalEvaluator = DefaultResultByMWErrorEvaluator.instance(PaymentServicesMiddlewareError.MDWPSM_005);
        return get(url, HeadersMW.getDefaultHeaders(parameters), AffiliatedServiceMWResponse.class, additionalEvaluator);
    }

    @Override
    public ListServicesMWResponse getServicesByCategoryAndCity(Integer subCategoryId, Integer cityId, Map<String, String> parameters) throws IOException {
        final String pathServices = String.format(PaymentServicesMiddlewareServices.GET_SERVICES.getServiceURL(), subCategoryId, cityId);
        final String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.PAYMENT_SERVICES.getName(), pathServices);
        DefaultResultByMWErrorEvaluator<ListServicesMWResponse> additionalEvaluator = DefaultResultByMWErrorEvaluator.instance(PaymentServicesMiddlewareError.MDWPSM_007);
        return get(url, HeadersMW.getDefaultHeaders(parameters), ListServicesMWResponse.class, additionalEvaluator);
    }

    @Override
    public DeleteAffiliateServiceMWResponse deleteAffiliationService(DeleteAffiliateServiceMWRequest request, Map<String, String> parameters) throws IOException {
        final String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.PAYMENT_SERVICES.getName(), PaymentServicesMiddlewareServices.DELETE_AFFILIATE_SERVICE.getServiceURL());
        return post(url, HeadersMW.getDefaultHeaders(parameters), request, DeleteAffiliateServiceMWResponse.class);
    }

    @Override
    public AffiliateCriteriaMWResponse getAffiliateCriteria(String personId, String serviceCode, Map<String, String> parameters) throws IOException {
        final String pathServices = String.format(PaymentServicesMiddlewareServices.GET_AFFILIATE_CRITERIA.getServiceURL(), serviceCode);
        final String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.PAYMENT_SERVICES.getName(), pathServices);
        return get(url, HeadersMW.getDefaultHeaders(parameters), AffiliateCriteriaMWResponse.class);
    }
}
