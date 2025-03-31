package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.remittance.mw.*;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.*;

import java.io.IOException;

public interface IRemittanceProvider {

    ListGeneralParametersMWResponse getGeneralParameters(GeneralParametersMWRequest request) throws IOException;

    ValidateAccountMWResponse validateAccount(ValidateAccountMWRequest request) throws IOException;

    MoneyOrderSentMWResponse getMoneyOrdersSent(MoneyOrderSentMWRequest request) throws IOException;

    CheckRemittanceMWResponse checkRemittance(CheckRemittanceMWRequest request) throws IOException;

    DepositRemittanceMWResponse depositRemittance(DepositRemittanceMWRequest request) throws IOException;

    DepositRemittanceMWResponse depositRemittanceWU(DepositRemittanceWUMWRequest request) throws IOException;

    UpdateWURemittanceMWResponse updateWURemittance(UpdateWURemittanceMWRequest request) throws IOException;

    CheckRemittanceMWResponse consultWURemittance(ConsultWURemittanceMWRequest request) throws IOException;
}
