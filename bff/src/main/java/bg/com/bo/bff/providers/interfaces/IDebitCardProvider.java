package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.debit.card.CreateAuthorizationOnlinePurchaseRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.providers.dtos.request.debit.card.CreateAuthorizationOnlinePurchaseMWRequest;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLimitsMWRequest;
import bg.com.bo.bff.providers.dtos.response.debit.card.AccountsDebitCardMWResponse;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLockStatusMWRequest;
import bg.com.bo.bff.providers.dtos.response.debit.card.ListDebitCardMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.CreateAuthorizationOnlinePurchaseMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCDetailMWResponse;

import java.io.IOException;
import java.util.Map;

public interface IDebitCardProvider {
    GenericResponse changeAmount(DCLimitsMWRequest request, Map<String, String> parameters) throws IOException;

    DCInternetAuthorizationNWResponse getListAuthorizations(String personId, String cardId, Map<String, String> parameters ) throws IOException;

    CreateAuthorizationOnlinePurchaseMWResponse createAuthorizationOnlinePurchase(CreateAuthorizationOnlinePurchaseMWRequest request, Map<String, String> parameter) throws IOException ;

    ListDebitCardMWResponse listDebitCard(Integer personId, Map<String, String> parameters) throws IOException;

    AccountsDebitCardMWResponse  accountListDebitCard(Integer personId, Integer cardId, Map<String, String> parameters) throws IOException;

    DCDetailMWResponse detail(String personId, String cardId, Map<String, String> parameters) throws IOException;

    GenericResponse lockStatus(DCLockStatusMWRequest requestMW, Map<String, String> parameters) throws IOException;
}
