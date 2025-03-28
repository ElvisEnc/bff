package bg.com.bo.bff.mappings.providers.remittance;

import bg.com.bo.bff.application.dtos.request.remittance.DepositRemittanceRequest;
import bg.com.bo.bff.application.dtos.request.remittance.DepositRemittanceWURequest;
import bg.com.bo.bff.application.dtos.response.remittance.CheckRemittanceResponse;
import bg.com.bo.bff.application.dtos.response.remittance.DepositRemittanceResponse;
import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.application.dtos.response.remittance.MoneyOrderSentResponse;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.*;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.CheckRemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.DepositRemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ListGeneralParametersMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.MoneyOrderSentMWResponse;

import java.util.List;


public interface IRemittanceMapper {

    GeneralParametersMWRequest mapperRequest(String personId);

    ValidateAccountMWRequest mapperRequest(String personId, String accountId);

    MoneyOrderSentMWRequest mapperRequestOrders(String personId);

    CheckRemittanceMWRequest mapperRequestRemittance(String personId, String remittanceId);

    DepositRemittanceMWRequest mapperRequestDeposit(String personId, String remittanceId, DepositRemittanceRequest request);

    DepositRemittanceWUMWRequest mapperRequestDepositWU(String personId, String remittanceId, DepositRemittanceWURequest request);

    ListGeneralParametersResponse convertResponse(ListGeneralParametersMWResponse mwResponse);

    List<MoneyOrderSentResponse> convertResponse(MoneyOrderSentMWResponse mwResponse);

    List<CheckRemittanceResponse> convertResponse(CheckRemittanceMWResponse mwResponse);

    List<DepositRemittanceResponse> convertResponse(DepositRemittanceMWResponse mwResponse);
}
