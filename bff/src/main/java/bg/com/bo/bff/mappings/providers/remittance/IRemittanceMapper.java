package bg.com.bo.bff.mappings.providers.remittance;

import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.GeneralParametersMWRequest;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ListGeneralParametersMWResponse;


public interface IRemittanceMapper {

    GeneralParametersMWRequest mapperRequest(String personId);

    ListGeneralParametersResponse convertResponse(ListGeneralParametersMWResponse mwResponse);
}
