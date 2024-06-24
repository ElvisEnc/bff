package bg.com.bo.bff.providers.mappings.payment.services;

import bg.com.bo.bff.application.dtos.response.SubcategoriesResponse;
import bg.com.bo.bff.providers.dtos.response.payment.services.SubcategoriesMWResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentServicesMapper implements IPaymentServicesMapper {
    @Override
    public SubcategoriesResponse convertResponse(SubcategoriesMWResponse response) {

        return new SubcategoriesResponse(
                response.getData()
                        .stream()
                        .map(x ->new SubcategoriesResponse.Subcategory(x.getSubCategoryCod(),x.getCategoryCod(),x.getDescription()))
                        .toList());

    }
}
