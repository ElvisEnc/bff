package bg.com.bo.bff.mappings.providers.services;

import bg.com.bo.bff.application.dtos.request.payment.service.DebtsRequest;
import bg.com.bo.bff.application.dtos.response.payment.service.*;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DebtsConsultationMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.AffiliateCriteriaMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.*;


import java.util.List;

public interface IPaymentServicesMapper {
    SubcategoriesResponse convertResponse(SubcategoriesMWResponse response);

    List<CategoryResponse> convertResponse(CategoryMWResponse mwResponse);

    SubCategoryCitiesResponse convertResponse(SubCategoryCitiesMWResponse response);

    List<AffiliateServiceResponse> convertResponse(AffiliatedServiceMWResponse mwResponse);

    DebtsConsultationMWRequest mapperRequest(Integer personId, Integer affiliateServiceId, DebtsRequest request);

    DebtsResponse convertDebtsResponse(DebtsConsultationMWResponse mwRequest);

    ListServicesResponse convertResponse(ListServicesMWResponse response);

    DeleteAffiliateServiceMWRequest convertRequest(String personId, String affiliateServiceId);

    AffiliateCriteriaResponse convertResponse(AffiliateCriteriaMWResponse mwResponse);
}
