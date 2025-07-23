package bg.com.bo.bff.mappings.providers.services;

import bg.com.bo.bff.application.dtos.request.payment.service.AffiliationDebtsRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.PaymentDebtsRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.PaymentTypeRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.ServiceAffiliationRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.ValidateAffiliateCriteriaRequest;
import bg.com.bo.bff.application.dtos.response.payment.service.*;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.ConceptsMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DebtsConsultationMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.ValidateAffiliateCriteriaMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.PaymentDebtsMWRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.ServiceAffiliationMWRequest;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.AffiliateCriteriaMWResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.*;


import java.util.List;

public interface IPaymentServicesMapper {
    SubcategoriesResponse convertResponse(SubcategoriesMWResponse response);

    List<CategoryResponse> convertResponse(CategoryMWResponse mwResponse);

    SubCategoryCitiesResponse convertResponse(SubCategoryCitiesMWResponse response);

    List<AffiliatedService> convertResponse(AffiliatedServiceMWResponse mwResponse);

    List<AffiliatedServicesResponse> convertResponse(List<AffiliatedService> response);

    DebtsConsultationMWRequest mapperRequest(Integer personId, Integer affiliateServiceId, AffiliationDebtsRequest request);

    PaymentDebtsMWRequest mapperRequest(String personId, String affiliateServiceId, PaymentDebtsRequest request);

    ServiceAffiliationMWRequest mapperRequest(String personId, ServiceAffiliationRequest request);

    AffiliationDebtsResponse convertDebtsResponse(DebtsConsultationMWResponse mwRequest);

    PaymentDebtsResponse convertPaymentResponse(PaymentDebtsMWResponse mwResponse);

    ServiceAffiliationResponse convertServiceAffiliationResponse(ServiceAffiliationMWResponse mwRequest);

    List<ServiceResponse> convertResponse(ListServicesMWResponse response);

    DeleteAffiliateServiceMWRequest convertRequest(String personId, String affiliateServiceId);

    AffiliateCriteriaResponse convertResponse(AffiliateCriteriaMWResponse mwResponse);

    ValidateAffiliateCriteriaMWRequest mapperRequest(String personId, String serviceCode, ValidateAffiliateCriteriaRequest request);

    ValidateAffiliateCriteriaResponse convertResponse(ValidateAffiliateCriteriaMWResponse mwResponse);

    List<PaymentTypeResponse> convertPaymentTypeResponse(ConceptsMWResponse mdwResponse);

    ConceptsMWRequest convertPaymentTypeRequest(PaymentTypeRequest request, String personId);
}
