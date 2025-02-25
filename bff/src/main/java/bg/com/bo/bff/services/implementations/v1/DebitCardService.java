package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.debit.card.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.AccountTD;
import bg.com.bo.bff.application.dtos.response.debit.card.DebitCard;
import bg.com.bo.bff.application.dtos.response.debit.card.ListAccountTDResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.ListDebitCardResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.InternetAuthorizationResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.providers.dtos.request.debit.card.mw.*;
import bg.com.bo.bff.providers.dtos.response.debit.card.mw.*;
import bg.com.bo.bff.providers.interfaces.IDebitCardProvider;
import bg.com.bo.bff.mappings.providers.card.IDebitCardMapper;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.CreateAuthorizationOnlinePurchaseResponse;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareResponse;
import bg.com.bo.bff.services.interfaces.IDebitCardService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class DebitCardService implements IDebitCardService {
    private final IDebitCardProvider idcProvider;
    private final IDebitCardMapper idcMapper;
    private static final String ACTION_CREATE_AUTHORIZATION_ONLINE_PURCHASE = "A";

    public DebitCardService(IDebitCardProvider idcProvider, IDebitCardMapper idcMapper) {
        this.idcProvider = idcProvider;
        this.idcMapper = idcMapper;
    }

    @Override
    public GenericResponse changeAmount(String personId, String cardId, DCLimitsRequest request) throws IOException {
        DCLimitsMWRequest mwRequest = idcMapper.mapToLimitsRequest(request, personId, cardId);
        DCLimitsMWResponse response = idcProvider.changeAmount(mwRequest);
        if (response.getData().getPciId() != null) {
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT);
        }
        return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_CHANGE_AMOUNT);
    }

    @Override
    public ListDebitCardResponse getListDebitCard(Integer personId) throws IOException {
        ListDebitCardMWResponse listDebitCardMWResponse = idcProvider.listDebitCard(personId);
        List<DebitCard> list = idcMapper.convertResponseListDebitCard(listDebitCardMWResponse);
        return ListDebitCardResponse.builder()
                .data(list)
                .build();
    }

    @Override
    public ListAccountTDResponse getAccountsTD(Integer personId, Integer cardId) throws IOException {
        AccountsDebitCardMWResponse mwResponse = idcProvider.accountListDebitCard(personId, cardId);
        List<AccountTD> list = idcMapper.convertResponseAccountListTD(mwResponse);
        return ListAccountTDResponse.builder()
                .data(list)
                .build();
    }

    @Override
    public InternetAuthorizationResponse getListAuthorizations(String personId, String cardId) throws IOException {
        DCInternetAuthorizationNWResponse result = idcProvider.getListAuthorizations(personId, cardId);
        return idcMapper.mapToInternetAuthorizationResponse(result);
    }

    @Override
    public GenericResponse deleteAuthOnlinePurchases(Integer personId, Integer cardId, Integer authId) throws IOException {
        DeleteAuthPurchaseMWRequest mwRequest = idcMapper.mapDeleteAuthRequest(personId, cardId, authId);
        return idcProvider.deleteAuth(mwRequest);
    }

    @Override
    public GenericResponse activeDebitCardAssurance(Integer personId, Integer cardId, UpdateDebitCardAssuranceRequest request) throws IOException {
        UpdateDebitCardSecureMWRequest mwRequest = idcMapper.mapActiveAssuranceRequest(personId, cardId, request);
        return idcProvider.activeDebitCardSecure(mwRequest);
    }

    @Override
    public GenericResponse activateDebitCard(Integer personId, Integer cardId, ActivateDebitCardRequest request) throws IOException {
        ActivateDebitCardMWRequest mwRequest = idcMapper.mapActivateDebitCardRequest(personId, cardId);
        return idcProvider.activateDebitCard(mwRequest);
    }

    @Override
    public GenericResponse createAuthorizationOnlinePurchase(String personId, String cardId, CreateAuthorizationOnlinePurchaseRequest request) throws IOException {
        LocalDate startPeriod = LocalDate.parse(request.getPeriod().getStart());
        LocalDate endPeriod = LocalDate.parse(request.getPeriod().getEnd());

        if (endPeriod.isBefore(startPeriod)) {
            throw new GenericException(DebitCardMiddlewareError.END_DATE_MUST_BE_GREATER_THAN_START_DATE);
        }
        CreateAuthorizationOnlinePurchaseMWRequest requestMW = idcMapper.mapToCreateAuthorizationOnlinePurchaseMWRequest(request, cardId,
                startPeriod.getDayOfMonth(),
                endPeriod.getDayOfMonth(),
                ACTION_CREATE_AUTHORIZATION_ONLINE_PURCHASE);

        CreateAuthorizationOnlinePurchaseMWResponse result = idcProvider.createAuthorizationOnlinePurchase(requestMW);
        if (result.getData().getIdPci() != null) {
            return GenericResponse.instance(CreateAuthorizationOnlinePurchaseResponse.SUCCESS_CREATE);
        }
        return GenericResponse.instance(CreateAuthorizationOnlinePurchaseResponse.ERROR_CREATE);
    }

    @Override
    public DCDetailResponse detail(String personId, String cardId) throws IOException {
        return idcMapper.mapToDetailResponse(idcProvider.detail(personId, cardId));
    }

    @Override
    public GenericResponse lockStatus(String personId, String cardId, DCLockStatusRequest request) throws IOException {
        DCLockStatusMWRequest requestMW = idcMapper.mapToLockStatusRequest(personId, cardId, request);
        return idcProvider.lockStatus(requestMW);
    }

    @Override
    public GenericResponse modifyAccountsOrder(String personId, String cardId, DCAccountsOrderRequest body) throws IOException {
        DCAccountsOrderMWRequest requestMW = idcMapper.mapToAccountsOrderRequest(personId, cardId, body);
        return idcProvider.modifyAccountsOrder(requestMW);
    }

    @Override
    public GenericResponse changePinCard(String personId, String cardId, ChangePinRequest body) throws IOException {
        ChangePinMWRequest requestMW = idcMapper.mapToChangePinRequest(personId, cardId, body);
        return idcProvider.changePinCard(requestMW);
    }
}
