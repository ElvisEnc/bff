package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.payment.service.AffiliateServiceResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.CategoryResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubCategoryCitiesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubcategoriesResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IPaymentServicesService {

    List<CategoryResponse> getCategories(Map<String, String> parameter) throws IOException;

    SubcategoriesResponse getSubcategories(Integer categoryId, Map<String, String> parameter) throws IOException;

    SubCategoryCitiesResponse getSubcategoryCities(Integer subCategoryId, Map<String, String> parameters) throws IOException;

    List<AffiliateServiceResponse> getAffiliateServices(Integer personId, Map<String, String> parameter) throws IOException;
}
