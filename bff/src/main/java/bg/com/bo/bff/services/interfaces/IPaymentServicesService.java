package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.payment.services.SubCategoryCitiesResponse;
import bg.com.bo.bff.application.dtos.response.payment.services.SubcategoriesResponse;

import java.io.IOException;
import java.util.Map;

public interface IPaymentServicesService {

     SubcategoriesResponse getSubcategories(Integer categoryId, Map<String, String> parameter) throws IOException;

     SubCategoryCitiesResponse getSubcategoryCities(Integer subCategoryId, Map<String, String> parameters) throws IOException;

}
