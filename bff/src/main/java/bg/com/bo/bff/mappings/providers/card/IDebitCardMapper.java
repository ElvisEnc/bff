package bg.com.bo.bff.mappings.providers.card;

import bg.com.bo.bff.application.dtos.request.debit.card.CreateAuthorizationOnlinePurchaseRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.DCAccountsOrderRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLockStatusRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.UpdateDebitCardAssuranceRequest;
import bg.com.bo.bff.application.dtos.response.debit.card.AccountTD;
import bg.com.bo.bff.application.dtos.response.debit.card.DebitCard;
import bg.com.bo.bff.application.dtos.response.debit.card.InternetAuthorizationResponse;
import bg.com.bo.bff.providers.dtos.request.debit.card.mw.*;
import bg.com.bo.bff.providers.dtos.response.debit.card.mw.DCInternetAuthorizationNWResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.mw.AccountsDebitCardMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.mw.ListDebitCardMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.mw.DCDetailMWResponse;

import java.util.List;

public interface IDebitCardMapper {
    DCLimitsMWRequest mapToLimitsRequest(DCLimitsRequest request, String personId, String cardId);

    DCLockStatusMWRequest mapToLockStatusRequest(String personId, String cardId, DCLockStatusRequest request);

    DCAccountsOrderMWRequest mapToAccountsOrderRequest(String personId, String cardId, DCAccountsOrderRequest request);

    List<DebitCard> convertResponseListDebitCard(ListDebitCardMWResponse listDebitCardMWResponse);

    List<AccountTD> convertResponseAccountListTD(AccountsDebitCardMWResponse mwResponse);

    InternetAuthorizationResponse mapToInternetAuthorizationResponse(DCInternetAuthorizationNWResponse response);

    DeleteAuthPurchaseMWRequest mapDeleteAuthRequest(Integer personId, Integer cardId, Integer authId);

    UpdateDebitCardSecureMWRequest mapActiveAssuranceRequest(Integer personId, Integer cardId, UpdateDebitCardAssuranceRequest request);

    ActivateDebitCardMWRequest mapActivateDebitCardRequest(Integer personId, Integer cardId);

    CreateAuthorizationOnlinePurchaseMWRequest mapToCreateAuthorizationOnlinePurchaseMWRequest(CreateAuthorizationOnlinePurchaseRequest request, String cardId, Integer intInitial, Integer intFinal, String action);

    DCDetailResponse mapToDetailResponse(DCDetailMWResponse response);
}
