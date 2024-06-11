package bg.com.bo.bff.providers.mappings.debit.card;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.application.dtos.response.debit.card.AccountTD;
import bg.com.bo.bff.application.dtos.response.debit.card.DebitCard;
import bg.com.bo.bff.application.dtos.response.debitcard.InternetAuthorizationResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponse;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLimitsMWRequest;
import bg.com.bo.bff.providers.dtos.response.debit.card.AccountsDebitCardMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.ListDebitCardMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCDetailMWResponse;

import java.util.List;

public interface IDebitCardMapper {
    DCLimitsMWRequest mapToLimitsRequest(DCLimitsRequest request, String personId, String cardId);

    List<DebitCard> convertResponseListDebitCard(ListDebitCardMWResponse listDebitCardMWResponse);

    List<AccountTD> convertResponseAccountListTD(AccountsDebitCardMWResponse mwResponse);

    InternetAuthorizationResponse mapToInternetAuthorizationResponse(DCInternetAuthorizationNWResponse response);
    DCDetailResponse mapToDetailResponse(DCDetailMWResponse response);
}
