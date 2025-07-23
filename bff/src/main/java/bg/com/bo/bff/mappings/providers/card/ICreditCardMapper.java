package bg.com.bo.bff.mappings.providers.card;

import bg.com.bo.bff.application.dtos.request.credit.card.*;
import bg.com.bo.bff.application.dtos.response.credit.card.*;
import bg.com.bo.bff.providers.dtos.request.credit.card.*;
import bg.com.bo.bff.providers.dtos.response.credit.card.mw.*;

import java.math.BigDecimal;
import java.util.List;

public interface ICreditCardMapper {
    ListCreditCardResponse convertResponse(ListCreditCardMWResponse mwResponse);

    DetailCreditCardResponse convertDetails(DetailsCreditCardMWResponse mwResponse);

    DetailPrepaidCardResponse convertDetails(DetailPrepaidCardMWResponse mwResponse);

    BlockCreditCardMWRequest mapperRequest(BlockCreditCardRequest request, String personId);

    AuthorizationCreditCardMWRequest mapperRequest(String personId, AuthorizationCreditCardRequest request);

    AvailableCreditCardResponse convertAvailable(AvailableCreditCardMWResponse mwResponse);

    List<PeriodCreditCardResponse> convertPeriods(PeriodCreditCardMWResponse mwResponse);

    CashAdvanceFeeMWRequest mapperRequest(String personId, String cmsAccountNumber, BigDecimal amount);

    CashAdvanceFeeResponse convertResponse(CashAdvanceFeeMWResponse mwResponse);

    List<LinkserCreditCardResponse> convertListCreditCard(LinkserCreditCardMWResponse mwResponse);

    CashAdvanceMWRequest mapperRequestAdvance(String personId, CashAdvanceRequest request);

    CashAdvanceResponse convertResponse(CashAdvanceMWResponse mwResponse);

    List<CreditCardStatementsResponse> convertStatements(CreditCardStatementsMWResponse mwResponse);

    List<PurchaseAuthResponse> convertPurchase(PurchaseAuthMWResponse mwResponse, String type);

    PayCreditCardMWRequest mapperRequest(String personId, String accountId, PayCreditCardRequest request);

    PayCreditCardResponse convertPayCC(PayCreditCardMWResponse mwResponse);

    FeePrepaidCardMWRequest mapperRequest(String cardId, FeePrepaidCardRequest request);

    FeePrepaidCardResponse convertResponse(FeePrepaidCardMWResponse mwResponse);
}
