package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.payment.services.SubcategoriesMWResponse;

import java.io.IOException;
import java.util.Map;

public interface IPaymentServicesProvider {

    SubcategoriesMWResponse getSubcategories(Integer categoryId, Map<String, String> parameters) throws IOException;
}
