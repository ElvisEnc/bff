package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DebtsConsultationMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.*;

import java.io.IOException;
import java.util.Map;

public interface IPaymentServicesProvider {

    CategoryMWResponse getCategories(Map<String, String> parameters) throws IOException;

    SubcategoriesMWResponse getSubcategories(Integer categoryId, Map<String, String> parameters) throws IOException;

    SubCategoryCitiesMWResponse getSubcategoryCities(Integer subCategoryId, Map<String, String> parameters) throws IOException;

    AffiliatedServiceMWResponse getAffiliationsServices(Integer personId, Map<String, String> parameters) throws IOException;

    DebtsConsultationMWResponse debtsConsultation(DebtsConsultationMWRequest request, Map<String, String> parameters) throws IOException;

    ListServicesMWResponse getServicesByCategoryAndCity(Integer subCategoryId, Integer cityId, Map<String, String> parameters) throws IOException;

    DeleteAffiliateServiceMWResponse deleteAffiliationService(DeleteAffiliateServiceMWRequest request, Map<String, String> parameter) throws IOException;

    AffiliateCriteriaMWResponse getAffiliateCriteria (String personId, String serviceCode, Map<String, String> parameter) throws IOException;
}
