package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.payment.service.DebtsRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IPaymentServicesService {

    List<CategoryResponse> getCategories(Map<String, String> parameter) throws IOException;

    SubcategoriesResponse getSubcategories(Integer categoryId, Map<String, String> parameter) throws IOException;

    SubCategoryCitiesResponse getSubcategoryCities(Integer subCategoryId, Map<String, String> parameters) throws IOException;

    List<AffiliateServiceResponse> getAffiliateServices(Integer personId, Map<String, String> parameter) throws IOException;

    DebtsResponse getAffiliationDebts(Integer personId, Integer affiliateServiceId, DebtsRequest request, Map<String, String> parameter) throws IOException;

    ListServicesResponse getServicesByCategoryAndCity(Integer subCategoryId, Integer cityId, Map<String, String> parameters) throws IOException;

    GenericResponse deleteAffiliationService(String personId, String affiliateServiceId, Map<String, String> parameter) throws IOException;

    AffiliateCriteriaResponse getAffiliateCriteria (String personId, String serviceCode, Map<String, String> parameter) throws IOException;
}
