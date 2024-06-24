package bg.com.bo.bff.providers.mappings.payment.services;


import bg.com.bo.bff.application.dtos.response.SubcategoriesResponse;
import bg.com.bo.bff.providers.dtos.response.payment.services.SubcategoriesMWResponse;

public interface IPaymentServicesMapper {
    SubcategoriesResponse convertResponse(SubcategoriesMWResponse response);
}
