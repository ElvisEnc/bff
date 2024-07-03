package bg.com.bo.bff.mappings.providers.services;

import bg.com.bo.bff.application.dtos.response.payment.service.AffiliateServiceResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.CategoryResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.ListServicesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubCategoryCitiesResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.SubcategoriesResponse;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.AffiliatedServiceMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.CategoryMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.ListServicesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.SubCategoryCitiesMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.SubcategoriesMWResponse;


import java.util.List;

public interface IPaymentServicesMapper {
    SubcategoriesResponse convertResponse(SubcategoriesMWResponse response);

    List<CategoryResponse> convertResponse(CategoryMWResponse mwResponse);

    SubCategoryCitiesResponse convertResponse(SubCategoryCitiesMWResponse response);

    List<AffiliateServiceResponse> convertResponse(AffiliatedServiceMWResponse mwResponse);

    ListServicesResponse convertResponse(ListServicesMWResponse response);

    DeleteAffiliateServiceMWRequest convertRequest(String personId, String affiliateServiceId);
}
