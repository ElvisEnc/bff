package bg.com.bo.bff.mappings.providers.remittance;

import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.application.dtos.response.remittance.MoneyOrderSentResponse;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.GeneralParametersMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.MoneyOrderSentMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.ValidateAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ListGeneralParametersMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.MoneyOrderSentMWResponse;

import java.util.List;


public interface IRemittanceMapper {

    GeneralParametersMWRequest mapperRequest(String personId);

    ValidateAccountMWRequest mapperRequest(String personId,String accountId);

    MoneyOrderSentMWRequest mapperRequestOrders(String personId);

    ListGeneralParametersResponse convertResponse(ListGeneralParametersMWResponse mwResponse);

    List<MoneyOrderSentResponse> convertResponse(MoneyOrderSentMWResponse mwResponse);
}
