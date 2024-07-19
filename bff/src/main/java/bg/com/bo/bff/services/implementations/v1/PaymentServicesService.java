package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.payment.service.AffiliationDebtsRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.ListServiceRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.PaymentDebtsRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.ServiceAffiliationRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.ValidateAffiliateCriteriaRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.*;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.commons.filters.ServiceNameFilter;
import bg.com.bo.bff.commons.filters.ServiceOrderFilter;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DebtsConsultationMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.ValidateAffiliateCriteriaMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.PaymentDebtsMWRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.ServiceAffiliationMWRequest;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.*;
import bg.com.bo.bff.providers.interfaces.IPaymentServicesProvider;
import bg.com.bo.bff.mappings.providers.services.IPaymentServicesMapper;
import bg.com.bo.bff.services.interfaces.IPaymentServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PaymentServicesService implements IPaymentServicesService {
    private final IPaymentServicesProvider provider;
    private final IPaymentServicesMapper mapper;
    private static final String PAYMENT_SERVICE_KEY = "payment_services";
    @Autowired
    private PaymentServicesService self;

    public PaymentServicesService(IPaymentServicesProvider provider, IPaymentServicesMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public List<CategoryResponse> getCategories(Map<String, String> parameter) throws IOException {
        CategoryMWResponse mwResponse = provider.getCategories(parameter);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public SubcategoriesResponse getSubcategories(Integer categoryId, Map<String, String> parameter) throws IOException {
        final SubcategoriesMWResponse result = provider.getSubcategories(categoryId, parameter);
        return mapper.convertResponse(result);
    }

    @Override
    public SubCategoryCitiesResponse getSubcategoryCities(Integer subCategoryId, Map<String, String> parameters) throws IOException {
        final SubCategoryCitiesMWResponse result = provider.getSubcategoryCities(subCategoryId, parameters);
        return mapper.convertResponse(result);
    }

    @Override
    public List<AffiliatedServicesResponse> getAffiliatedServices(Integer personId, Map<String, String> parameter) throws IOException {
        AffiliatedServiceMWResponse mwResponse = provider.getAffiliatedServices(personId, parameter);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public ServiceAffiliationResponse serviceAffiliation(String personId, ServiceAffiliationRequest request, Map<String, String> parameter) throws IOException {
        ServiceAffiliationMWRequest mwRequest = mapper.mapperRequest(personId, request);
        ServiceAffiliationMWResponse mwResponse = provider.serviceAffiliation(mwRequest, parameter);
        return mapper.convertServiceAffiliationResponse(mwResponse);
    }

    @Override
    public AffiliationDebtsResponse getAffiliationDebts(Integer personId, Integer affiliateServiceId, AffiliationDebtsRequest request, Map<String, String> parameter) throws IOException {
        DebtsConsultationMWRequest mwRequest = mapper.mapperRequest(personId, affiliateServiceId, request);
        DebtsConsultationMWResponse mwResponse = provider.debtsConsultation(mwRequest, parameter);
        return mapper.convertDebtsResponse(mwResponse);
    }

    @Override
    public PaymentDebtsResponse paymentDebts(String personId, String affiliateServiceId, PaymentDebtsRequest request, Map<String, String> parameter) throws IOException {
        PaymentDebtsMWRequest mwRequest = mapper.mapperRequest(personId, affiliateServiceId, request);
        PaymentDebtsMWResponse mwResponse = provider.paymentDebts(mwRequest, parameter);
        return mapper.convertPaymentResponse(mwResponse);
    }

    @Override
    public List<ServiceResponse> getServicesByCategoryAndCity(Integer subCategoryId, Integer cityId, Map<String, String> parameters) throws IOException {
        final ListServicesMWResponse result = provider.getServicesByCategoryAndCity(subCategoryId, cityId, parameters);
        return mapper.convertResponse(result);
    }

    @Override
    public GenericResponse deleteAffiliationService(String personId, String accountNumber, Map<String, String> parameter) throws IOException {
        final DeleteAffiliateServiceMWRequest request = mapper.convertRequest(personId, accountNumber);
        provider.deleteAffiliationService(request, parameter);
        return GenericResponse.instance(DeleteAffiliateServiceResponse.SUCCESS);
    }

    @Override
    public AffiliateCriteriaResponse getAffiliateCriteria(String personId, String serviceCode, Map<String, String> parameters) throws IOException {
        final AffiliateCriteriaMWResponse mwResponse = provider.getAffiliateCriteria(personId, serviceCode, parameters);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public ValidateAffiliateCriteriaResponse validateAffiliateCriteria(String personId, String serviceCode, ValidateAffiliateCriteriaRequest request, Map<String, String> parameter) throws IOException {
        ValidateAffiliateCriteriaMWRequest mwRequest = mapper.mapperRequest(personId, serviceCode, request);
        final ValidateAffiliateCriteriaMWResponse mwResponse = provider.validateAffiliateCriteria(mwRequest, parameter);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public List<ServiceResponse> getListService(ListServiceRequest request, Map<String, String> parameter) throws IOException {
        Boolean isInitial = request.getFilters().getPagination() == null || request.getFilters().getPagination().getPage() == null || request.getFilters().getPagination().getPage() == 1;
        List<ServiceResponse> list = self.getServiceCache(parameter, PAYMENT_SERVICE_KEY, isInitial);

        if (request.getFilters().getOrder() != null) {
            list = new ServiceOrderFilter(request.getFilters()).apply(list);
        }

        if (request.getFilters().getSearch() != null && !request.getFilters().getSearch().isEmpty()) {
            list = new ServiceNameFilter(request.getFilters().getSearch()).apply(list);
        }

        if (request.getFilters().getPagination() != null) {
            int page = request.getFilters().getPagination().getPage();
            int pageSize = request.getFilters().getPagination().getPageSize();
            list = new PageFilter(page, pageSize).apply(list);
        }
        return list;
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.GENERIC_DATA, key = "#key", condition = "#isInitial == false")},
            put = {@CachePut(value = CacheConstants.GENERIC_DATA, key = "#key", condition = "#isInitial == true")})
    protected List<ServiceResponse> getServiceCache(Map<String, String> parameter, String key, Boolean isInitial) throws IOException {
        ListServicesMWResponse mwResponse = provider.getListService(parameter);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }
}
