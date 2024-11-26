package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.request.debit.card.mw.*;
import bg.com.bo.bff.providers.dtos.response.debit.card.mw.*;

import java.io.IOException;
import java.util.Map;

public interface IDebitCardProvider {
    GenericResponse changeAmount(DCLimitsMWRequest request) throws IOException;

    DCInternetAuthorizationNWResponse getListAuthorizations(String personId, String cardId) throws IOException;

    CreateAuthorizationOnlinePurchaseMWResponse createAuthorizationOnlinePurchase(CreateAuthorizationOnlinePurchaseMWRequest request) throws IOException;

    ListDebitCardMWResponse listDebitCard(Integer personId) throws IOException;

    AccountsDebitCardMWResponse accountListDebitCard(Integer personId, Integer cardId) throws IOException;

    GenericResponse deleteAuth(DeleteAuthPurchaseMWRequest mwRequest) throws IOException;

    GenericResponse activeDebitCardSecure(UpdateDebitCardSecureMWRequest request) throws IOException;

    GenericResponse activateDebitCard(ActivateDebitCardMWRequest request) throws IOException;

    DCDetailMWResponse detail(String personId, String cardId) throws IOException;

    GenericResponse lockStatus(DCLockStatusMWRequest requestMW) throws IOException;

    GenericResponse modifyAccountsOrder(DCAccountsOrderMWRequest requestMW) throws IOException;

    GenericResponse changePinCard(ChangePinMWRequest requestMW) throws IOException;
}
