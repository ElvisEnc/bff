package bg.com.bo.bff.providers.mappings.payment.services;

import bg.com.bo.bff.application.dtos.response.payment.service.AffiliateServiceResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.CategoryResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.ListServicesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubCategoryCitiesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubcategoriesResponse;
import bg.com.bo.bff.providers.dtos.request.payment.services.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.response.payment.service.AffiliatedServiceMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.CategoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.ListServicesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.SubCategoryCitiesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.SubcategoriesMWResponse;


import java.util.List;

public interface IPaymentServicesMapper {
    SubcategoriesResponse convertResponse(SubcategoriesMWResponse response);

    List<CategoryResponse> convertResponse(CategoryMWResponse mwResponse);

    SubCategoryCitiesResponse convertResponse(SubCategoryCitiesMWResponse response);

    List<AffiliateServiceResponse> convertResponse(AffiliatedServiceMWResponse mwResponse);

    ListServicesResponse convertResponse(ListServicesMWResponse response);

    DeleteAffiliateServiceMWRequest convertRequest(String personId, String affiliateServiceId);
}
