package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.credit.card.*;
import bg.com.bo.bff.application.dtos.response.credit.card.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


public interface ICreditCardService {
    ListCreditCardResponse getListCard(String personId) throws IOException;

    DetailCreditCardResponse getDetailsCreditCard(String personId, String cardId) throws IOException;

    DetailPrepaidCardResponse getDetailsPrepaidCard(String personId, String cardId) throws IOException;

    GenericResponse blockCreditCard(String personId, String cardId, BlockCreditCardRequest request) throws IOException;

    AvailableCreditCardResponse getAvailable(String personId, String cardId, String cmsCard) throws IOException;

    List<PeriodCreditCardResponse> getPeriods(String personId, String cardId) throws IOException;

    CashAdvanceFeeResponse getCashAdvanceFee(String personId, String cmsAccountNumber, BigDecimal amount) throws IOException;

    List<LinkserCreditCardResponse> getCreditCards(String personId, String cmsAccount) throws IOException;

    CashAdvanceResponse makeCashAdvance(String personId, String cardId, CashAdvanceRequest request) throws IOException;

    List<CreditCardStatementsResponse> creditCardStatements(String personId, CreditCardStatementRequest request) throws IOException;

    List<PurchaseAuthResponse> getPurchasesAuthorizations(String personId, String cmsCard, String type) throws IOException;

    PayCreditCardResponse payCreditCard(String personId, String accountId, PayCreditCardRequest request) throws IOException;

    GenericResponse authorizationCreditCard(String personId, AuthorizationCreditCardRequest request) throws IOException;

    FeePrepaidCardResponse getFeePrepaidCard(String personId, String cardId, FeePrepaidCardRequest request) throws IOException;
}
