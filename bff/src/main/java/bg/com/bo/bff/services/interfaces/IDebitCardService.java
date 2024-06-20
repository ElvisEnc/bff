package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.debit.card.CreateAuthorizationOnlinePurchaseRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.DCAccountsOrderRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLockStatusRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.UpdateDebitCardAssuranceRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.ListAccountTDResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.ListDebitCardResponse;
import bg.com.bo.bff.application.dtos.response.debitcard.InternetAuthorizationResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface IDebitCardService {

    GenericResponse changeAmount(String personId, String cardId, DCLimitsRequest request, Map<String, String> parameter) throws IOException;

    ListDebitCardResponse getListDebitCard(Integer personId, Map<String, String> parameter) throws IOException;

    ListAccountTDResponse getAccountsTD(Integer personId, Integer cardId, Map<String, String> parameter) throws IOException;

    GenericResponse deleteAuthOnlinePurchases(Integer personId, Integer cardId, Integer authId, Map<String, String> parameters) throws IOException;

    InternetAuthorizationResponse getListAuthorizations(String personId, String cardId, Map<String, String> parameter) throws IOException;

    GenericResponse activeDebitCardAssurance(Integer personId, Integer cardId, UpdateDebitCardAssuranceRequest request, Map<String, String> parameters) throws IOException;

    GenericResponse createAuthorizationOnlinePurchase(String personId, String cardId, CreateAuthorizationOnlinePurchaseRequest request, Map<String, String> parameter) throws IOException;

    DCDetailResponse detail(String personId, String cardId, Map<String, String> parameter) throws IOException;

    GenericResponse lockStatus(String personId, String cardId, DCLockStatusRequest request, Map<String, String> parameters) throws IOException;

    GenericResponse modifyAccountsOrder(String personId, String cardId, DCAccountsOrderRequest body, Map<String, String> parameters) throws IOException;
}
