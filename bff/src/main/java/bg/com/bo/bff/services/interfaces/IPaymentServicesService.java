package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.SubcategoriesResponse;

import java.io.IOException;
import java.util.Map;

public interface IPaymentServicesService {
     SubcategoriesResponse getSubcategories(Integer categoryId, Map<String, String> parameter) throws IOException;
}
