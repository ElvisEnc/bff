package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.credit.card.PayCreditCardMWRequest;
import bg.com.bo.bff.providers.dtos.response.credit.card.mw.PayCreditCardMWResponse;

import java.io.IOException;

public interface ICreditCardTransactionProvider {
    PayCreditCardMWResponse payCreditCard(PayCreditCardMWRequest request) throws IOException;
}
