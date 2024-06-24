package bg.com.bo.bff.providers.mappings.payment.services;

import bg.com.bo.bff.application.dtos.SubCategoryCitiesMWResponse;
import bg.com.bo.bff.application.dtos.response.payment.services.SubCategoryCitiesResponse;
import bg.com.bo.bff.application.dtos.response.payment.services.SubcategoriesResponse;
import bg.com.bo.bff.providers.dtos.response.payment.services.SubcategoriesMWResponse;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public SubCategoryCitiesResponse convertResponse(SubCategoryCitiesMWResponse response) {
        List<SubCategoryCitiesResponse.City> data = response.getData().stream().map(x -> new SubCategoryCitiesResponse.City(x.getId(), x.name)).toList();
        return new SubCategoryCitiesResponse(data);
    }
}
