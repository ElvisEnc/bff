package bg.com.bo.bff.mappings.providers.services;

import bg.com.bo.bff.application.dtos.response.payment.service.ListServicesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.AffiliateServiceResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubCategoryCitiesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.CategoryResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubcategoriesResponse;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.ListServicesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.AffiliatedServiceMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.CategoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.SubCategoryCitiesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.SubcategoriesMWResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class PaymentServicesMapper implements IPaymentServicesMapper {
    @Override
    public SubcategoriesResponse convertResponse(SubcategoriesMWResponse response) {
        return new SubcategoriesResponse(
                response.getData()
                        .stream()
                        .map(x -> new SubcategoriesResponse.Subcategory(x.getSubCategoryCod(), x.getCategoryCod(), x.getDescription(), Objects.equals(x.getHasCity(), "S")))
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

    @Override
    public ListServicesResponse convertResponse(ListServicesMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return new ListServicesResponse(Collections.emptyList());
        List<ListServicesResponse.Service> data =
                mwResponse.getData().stream()
                        .map(mw -> ListServicesResponse.Service.builder()
                                .serviceId(mw.getServiceCode())
                                .description(mw.getServiceName())
                                .build())
                        .toList();
        return new ListServicesResponse(data);
    }

    @Override
    public DeleteAffiliateServiceMWRequest convertRequest(String personId, String affiliateServiceId) {

        return DeleteAffiliateServiceMWRequest.builder()
                .affiliationCode(affiliateServiceId)
                .personId(personId)
                .build();

    }
}
