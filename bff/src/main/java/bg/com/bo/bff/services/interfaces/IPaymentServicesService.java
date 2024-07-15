package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.payment.service.AffiliationDebtsRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.PaymentDebtsRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.ServiceAffiliationRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.ValidateAffiliateCriteriaRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IPaymentServicesService {

    List<CategoryResponse> getCategories(Map<String, String> parameter) throws IOException;

    SubcategoriesResponse getSubcategories(Integer categoryId, Map<String, String> parameter) throws IOException;

    SubCategoryCitiesResponse getSubcategoryCities(Integer subCategoryId, Map<String, String> parameters) throws IOException;

    List<AffiliatedServicesResponse> getAffiliatedServices(Integer personId, Map<String, String> parameter) throws IOException;

    ServiceAffiliationResponse serviceAffiliation(String personId, ServiceAffiliationRequest request, Map<String, String> parameter) throws IOException;

    AffiliationDebtsResponse getAffiliationDebts(Integer personId, Integer affiliateServiceId, AffiliationDebtsRequest request, Map<String, String> parameter) throws IOException;

    PaymentDebtsResponse paymentDebts(String personId, String affiliateServiceId, PaymentDebtsRequest request, Map<String, String> parameter) throws IOException;

    ListServicesResponse getServicesByCategoryAndCity(Integer subCategoryId, Integer cityId, Map<String, String> parameters) throws IOException;

    GenericResponse deleteAffiliationService(String personId, String affiliateServiceId, Map<String, String> parameter) throws IOException;

    AffiliateCriteriaResponse getAffiliateCriteria(String personId, String serviceCode, Map<String, String> parameter) throws IOException;

    ValidateAffiliateCriteriaResponse validateAffiliateCriteria (String personId, String serviceCode, ValidateAffiliateCriteriaRequest request, Map<String, String> parameter) throws IOException;
}
