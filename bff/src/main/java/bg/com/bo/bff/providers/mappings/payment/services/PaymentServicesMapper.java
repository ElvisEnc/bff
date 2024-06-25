package bg.com.bo.bff.providers.mappings.payment.services;

import bg.com.bo.bff.application.dtos.SubCategoryCitiesMWResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.AffiliateServiceResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubCategoryCitiesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.CategoryResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubcategoriesResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.AffiliatedServiceMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.CategoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.SubcategoriesMWResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class PaymentServicesMapper implements IPaymentServicesMapper {
    @Override
    public SubcategoriesResponse convertResponse(SubcategoriesMWResponse response) {
        return new SubcategoriesResponse(
                response.getData()
                        .stream()
                        .map(x -> new SubcategoriesResponse.Subcategory(x.getSubCategoryCod(), x.getCategoryCod(), x.getDescription()))
                        .toList());
    }

    @Override
    public List<CategoryResponse> convertResponse(CategoryMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> CategoryResponse.builder()
                        .categoryId(mw.getIdCategory())
                        .categoryName(mw.getDescription())
                        .build())
                .toList();
    }

    @Override
    public SubCategoryCitiesResponse convertResponse(SubCategoryCitiesMWResponse response) {
        List<SubCategoryCitiesResponse.City> data = response.getData().stream().map(x -> new SubCategoryCitiesResponse.City(x.getId(), x.name)).toList();
        return new SubCategoryCitiesResponse(data);
    }

    @Override
    public List<AffiliateServiceResponse> convertResponse(AffiliatedServiceMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> AffiliateServiceResponse.builder()
                        .affiliateServiceId(mw.getAffiliationCode())
                        .serviceId(mw.getServiceCode())
                        .serviceName(mw.getServiceDesc())
                        .referenceName(mw.getReferenceName())
                        .nameHolder(mw.getNameHolder())
                        .build())
                .toList();
    }
}
