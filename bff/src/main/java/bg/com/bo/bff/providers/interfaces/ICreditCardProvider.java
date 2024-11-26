package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.request.credit.card.*;
import bg.com.bo.bff.providers.dtos.response.credit.card.mw.*;

import java.io.IOException;

public interface ICreditCardProvider {
    ListCreditCardMWResponse getListCreditCard(String personId) throws IOException;

    DetailsCreditCardMWResponse getDetailCreditCard(String personId, String cardId) throws IOException;

    DetailPrepaidCardMWResponse getDetailPrepaidCard(String personId, String cardId) throws IOException;

    GenericResponse blockCreditCard(BlockCreditCardMWRequest mwRequest) throws IOException;

    AvailableCreditCardMWResponse getAvailable(String cmsCard) throws IOException;

    PeriodCreditCardMWResponse getPaymentPeriods() throws IOException;

    CashAdvanceFeeMWResponse getCashAdvanceFee(CashAdvanceFeeMWRequest request) throws IOException;

    LinkserCreditCardMWResponse getCreditCardsFromLinkser(String personId, String cmsAccount) throws IOException;

    CashAdvanceMWResponse cashAdvance(CashAdvanceMWRequest mwRequest) throws IOException;

    CreditCardStatementsMWResponse getStatements(String cmsCard, String init, String end) throws IOException;

    PurchaseAuthMWResponse getListPurchaseAuth(String personId, String cmsCard) throws IOException;

    GenericResponse authorizationCreditCard(AuthorizationCreditCardMWRequest mwRequest) throws IOException;

    FeePrepaidCardMWResponse getFeePrepaidCard(FeePrepaidCardMWRequest mwRequest) throws IOException;
}
