package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.AffiliateServiceResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.CategoryResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.ListServicesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubCategoryCitiesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubcategoriesResponse;
import bg.com.bo.bff.application.dtos.response.payment.services.DeleteAffiliateServiceResponse;
import bg.com.bo.bff.providers.dtos.request.payment.services.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.response.payment.service.AffiliatedServiceMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.CategoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.ListServicesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.SubCategoryCitiesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.SubcategoriesMWResponse;
import bg.com.bo.bff.providers.interfaces.IPaymentServicesProvider;
import bg.com.bo.bff.providers.mappings.payment.services.IPaymentServicesMapper;
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
    public List<AffiliateServiceResponse> getAffiliateServices(Integer personId, Map<String, String> parameter) throws IOException {
        AffiliatedServiceMWResponse mwResponse = provider.getAffiliationsServices(personId, parameter);
        return mapper.convertResponse(mwResponse);
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
}
