package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.debit.card.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.ListAccountTDResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.ListDebitCardResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.InternetAuthorizationResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface IDebitCardService {

    GenericResponse changeAmount(String personId, String cardId, DCLimitsRequest request) throws IOException;

    ListDebitCardResponse getListDebitCard(Integer personId) throws IOException;

    ListAccountTDResponse getAccountsTD(Integer personId, Integer cardId) throws IOException;

    GenericResponse deleteAuthOnlinePurchases(Integer personId, Integer cardId, Integer authId) throws IOException;

    InternetAuthorizationResponse getListAuthorizations(String personId, String cardId) throws IOException;

    GenericResponse activeDebitCardAssurance(Integer personId, Integer cardId, UpdateDebitCardAssuranceRequest request) throws IOException;

    GenericResponse activateDebitCard(Integer personId, Integer cardId, ActivateDebitCardRequest request) throws IOException;

    GenericResponse createAuthorizationOnlinePurchase(String personId, String cardId, CreateAuthorizationOnlinePurchaseRequest request) throws IOException;

    DCDetailResponse detail(String personId, String cardId) throws IOException;

    GenericResponse lockStatus(String personId, String cardId, DCLockStatusRequest request) throws IOException;

    GenericResponse modifyAccountsOrder(String personId, String cardId, DCAccountsOrderRequest body) throws IOException;

    GenericResponse changePinCard(String personId, String cardId, ChangePinRequest body) throws IOException;
}
