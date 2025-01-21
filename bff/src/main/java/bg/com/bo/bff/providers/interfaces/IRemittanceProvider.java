package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.remittance.mw.GeneralParametersMWRequest;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ListGeneralParametersMWResponse;

import java.io.IOException;

public interface IRemittanceProvider {

    ListGeneralParametersMWResponse getGeneralParameters(GeneralParametersMWRequest request) throws IOException;
}
