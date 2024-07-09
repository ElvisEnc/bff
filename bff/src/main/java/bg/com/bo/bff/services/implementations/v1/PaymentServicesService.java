package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.payment.service.AffiliationDebtsRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.ServiceAffiliationRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.*;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DebtsConsultationMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.ServiceAffiliationMWRequest;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.*;
import bg.com.bo.bff.providers.interfaces.IPaymentServicesProvider;
import bg.com.bo.bff.mappings.providers.services.IPaymentServicesMapper;
import bg.com.bo.bff.services.interfaces.IPaymentServicesService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServicesService implements IPaymentServicesService {
    private final IPaymentServicesProvider provider;
    private final IPaymentServicesMapper mapper;

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
    public ListServicesResponse getServicesByCategoryAndCity(Integer subCategoryId, Integer cityId, Map<String, String> parameters) throws IOException {
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
}
