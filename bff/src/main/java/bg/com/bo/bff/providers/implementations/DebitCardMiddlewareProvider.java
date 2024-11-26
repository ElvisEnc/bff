package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.debit.card.mw.*;
import bg.com.bo.bff.providers.dtos.response.debit.card.mw.*;
import bg.com.bo.bff.providers.interfaces.IDebitCardProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareResponse;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.*;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class DebitCardMiddlewareProvider extends MiddlewareProvider<DebitCardMiddlewareError> implements IDebitCardProvider {
    private final HttpServletRequest httpServletRequest;

    public DebitCardMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, HttpServletRequest httpServletRequest) {
        super(ProjectNameMW.DEBIT_CARD_MANAGER, DebitCardMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientDebitCardManager());
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public GenericResponse changeAmount(DCLimitsMWRequest request) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.CHANGE_AMOUNT.getServiceURL();
        DCLimitsMWResponse response = patch(url, HeadersMW.getDefaultHeaders(httpServletRequest), request, DCLimitsMWResponse.class);
        if (response.getData().getPciId() != null) {
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT);
        } else {
            return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_CHANGE_AMOUNT);
        }
    }

    @Override
    public ListDebitCardMWResponse listDebitCard(Integer personId) throws IOException {
        String path = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.LIST_DEBIT_CARD.getServiceURL() + personId;
        ByMwErrorResponseHandler<ListDebitCardMWResponse> responseHandler = ByMwErrorResponseHandler.instance(DebitCardMiddlewareError.MDWTJD_004);
        return get(path, HeadersMW.getDefaultHeaders(httpServletRequest), ListDebitCardMWResponse.class, responseHandler);
    }

    @Override
    public AccountsDebitCardMWResponse accountListDebitCard(Integer personId, Integer cardId) throws IOException {
        String path = middlewareConfig.getUrlBase() +
                ProjectNameMW.DEBIT_CARD_MANAGER.getName() +
                String.format(DebitCardMiddlewareServices.ACCOUNT_LIST_DEBIT_CARD.getServiceURL(), cardId, personId);
        return get(path, HeadersMW.getDefaultHeaders(httpServletRequest), AccountsDebitCardMWResponse.class);
    }

    @Override
    public DCInternetAuthorizationNWResponse getListAuthorizations(String personId, String cardId) throws IOException {
        String path = String.format(DebitCardMiddlewareServices.GET_LIST_INTERNET_AUTHORIZATION.getServiceURL(), cardId, personId);
        String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.DEBIT_CARD_MANAGER.getName(), path);

        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), DCInternetAuthorizationNWResponse.class);
    }

    @Override
    public GenericResponse deleteAuth(DeleteAuthPurchaseMWRequest mwRequest) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.DELETE_LIMIT_INTERNET.getServiceURL();
        DeleteAuthPurchaseMWResponse mwResponse = deleteWithBody(url, HeadersMW.getDefaultHeaders(httpServletRequest), mwRequest, DeleteAuthPurchaseMWResponse.class);
        if (Objects.equals(mwResponse.getData().getIdPci(), mwRequest.getIdPci()))
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_DELETE_AUTH_PURCHASE);
        else return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_DELETE_AUTH_PURCHASE);
    }

    @Override
    public GenericResponse activeDebitCardSecure(UpdateDebitCardSecureMWRequest request) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.ACTIVE_SECURE.getServiceURL();
        UpdateSecureMWResponse mwResponse = post(url, HeadersMW.getDefaultHeaders(httpServletRequest), request, UpdateSecureMWResponse.class);
        if (mwResponse.getData().getIdPci() != null)
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_ACTIVE_ASSURANCE);
        else return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_ACTIVE_ASSURANCE);
    }

    @Override
    public GenericResponse activateDebitCard(ActivateDebitCardMWRequest request) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.ACTIVE_DEBIT_CARD.getServiceURL();
        UpdateSecureMWResponse mwResponse = patch(url, HeadersMW.getDefaultHeaders(httpServletRequest), request, UpdateSecureMWResponse.class);
        if (mwResponse.getData().getIdPci() != null)
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_ACTIVATE_DEBIT_CARD);
        else return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_ACTIVATE_DEBIT_CARD);
    }

    @Override
    public DCDetailMWResponse detail(String personId, String cardId) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + String.format(DebitCardMiddlewareServices.DETAIL.getServiceURL(), personId, cardId);
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), DCDetailMWResponse.class);
    }

    @Override
    public GenericResponse lockStatus(DCLockStatusMWRequest requestMW) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.UPDATE_LOCK_STATUS.getServiceURL();
        DCLockStatusMWResponse mwResponse = patch(url, HeadersMW.getDefaultHeaders(httpServletRequest), requestMW, DCLockStatusMWResponse.class);
        if (mwResponse.getData().getPciId() != null)
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_UPDATE_STATUS_LOCK);
        else return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_UPDATE_STATUS_LOCK);
    }

    @Override
    public CreateAuthorizationOnlinePurchaseMWResponse createAuthorizationOnlinePurchase(CreateAuthorizationOnlinePurchaseMWRequest request) throws IOException {
        String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.DEBIT_CARD_MANAGER.getName(), DebitCardMiddlewareServices.CREATE_AUTHORIZATION_ONLINE_PURCHASE.getServiceURL());
        return patch(url, HeadersMW.getDefaultHeaders(httpServletRequest), request, CreateAuthorizationOnlinePurchaseMWResponse.class);
    }

    @Override
    public GenericResponse modifyAccountsOrder(DCAccountsOrderMWRequest requestMW) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.MODIFY_ACCOUNTS_ORDER.getServiceURL();
        DCAccountsOrderMWResponse response = patch(url, HeadersMW.getDefaultHeaders(httpServletRequest), requestMW, DCAccountsOrderMWResponse.class);
        if (response.getData().getPciId() != null) {
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_MODIFY_ACCOUNTS_ORDER);
        } else {
            return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_MODIFY_ACCOUNTS_ORDER);
        }
    }

    @Override
    public GenericResponse changePinCard(ChangePinMWRequest requestMW) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.CHANGE_PIN_CARD.getServiceURL();
        ChangePinMWResponse response = patch(url, HeadersMW.getDefaultHeaders(httpServletRequest), requestMW, ChangePinMWResponse.class);
        if (response.getData().getIdPci() != null) {
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_PIN_CARD);
        } else {
            return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_CHANGE_PIN_CARD);
        }
    }
}
