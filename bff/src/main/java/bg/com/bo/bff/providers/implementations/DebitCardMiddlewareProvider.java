package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.enums.ProjectNameMW;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.debit.card.*;
import bg.com.bo.bff.providers.dtos.response.debit.card.*;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCDetailMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.CreateAuthorizationOnlinePurchaseMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCLimitsMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.ListDebitCardMWResponse;
import bg.com.bo.bff.providers.interfaces.IDebitCardProvider;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareResponse;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareServices;
import bg.com.bo.bff.providers.models.middleware.*;
import bg.com.bo.bff.providers.models.middleware.additional.evaluator.DefaultResultByMWErrorEvaluator;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class DebitCardMiddlewareProvider extends MiddlewareProvider<DebitCardMiddlewareError> implements IDebitCardProvider {
    public DebitCardMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        super(ProjectNameMW.DEBIT_CARD_MANAGER, DebitCardMiddlewareError.class, tokenMiddlewareProvider, middlewareConfig, httpClientFactory, middlewareConfig.getClientDebitCardManager());
    }

    @Override
    public GenericResponse changeAmount(DCLimitsMWRequest request, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.CHANGE_AMOUNT.getServiceURL();
        Header[] headers = setHeaders(parameters);
        DCLimitsMWResponse response = patch(url, headers, request, DCLimitsMWResponse.class);
        if (response.getData().getPciId() != null) {
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT);
        } else {
            return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_CHANGE_AMOUNT);
        }
    }

    private static Header[] setHeaders(Map<String, String> parameters) {
        return new Header[]{
                new BasicHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName()),
                new BasicHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode())),
                new BasicHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode())),
                new BasicHeader(DeviceMW.DEVICE_NAME.getCode(), parameters.get(DeviceMW.DEVICE_NAME.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_X.getCode(), parameters.get(DeviceMW.GEO_POSITION_X.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_Y.getCode(), parameters.get(DeviceMW.GEO_POSITION_Y.getCode())),
                new BasicHeader(DeviceMW.APP_VERSION.getCode(), parameters.get(DeviceMW.APP_VERSION.getCode())),
        };
    }

    @Override
    public ListDebitCardMWResponse listDebitCard(Integer personId, Map<String, String> parameters) throws IOException {
        String path = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.LIST_DEBIT_CARD.getServiceURL() + personId;
        DefaultResultByMWErrorEvaluator<ListDebitCardMWResponse> additionalEvaluator = DefaultResultByMWErrorEvaluator.instance(DebitCardMiddlewareError.MDWTJD_004);
        return get(path, setHeaders(parameters), ListDebitCardMWResponse.class, additionalEvaluator);
    }

    @Override
    public AccountsDebitCardMWResponse accountListDebitCard(Integer personId, Integer cardId, Map<String, String> parameters) throws IOException {
        String path = middlewareConfig.getUrlBase() +
                ProjectNameMW.DEBIT_CARD_MANAGER.getName() +
                String.format(DebitCardMiddlewareServices.ACCOUNT_LIST_DEBIT_CARD.getServiceURL(), cardId, personId);
        return get(path, setHeaders(parameters), AccountsDebitCardMWResponse.class);
    }

    @Override
    public DCInternetAuthorizationNWResponse getListAuthorizations(String personId, String cardId, Map<String, String> parameters) throws IOException {
        String path = String.format(DebitCardMiddlewareServices.GET_LIST_INTERNET_AUTHORIZATION.getServiceURL(), cardId, personId);
        String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.DEBIT_CARD_MANAGER.getName(), path);
        Header[] headers = {
                new BasicHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName()),
                new BasicHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode())),
                new BasicHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode()))
        };
        return get(url, headers, DCInternetAuthorizationNWResponse.class);
    }

    @Override
    public GenericResponse deleteAuth(DeleteAuthPurchaseMWRequest mwRequest, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.DELETE_LIMIT_INTERNET.getServiceURL();
        DeleteAuthPurchaseMWResponse mwResponse = deleteWithBody(url, setHeaders(parameters), mwRequest, DeleteAuthPurchaseMWResponse.class);
        if (Objects.equals(mwResponse.getData().getIdPci(), mwRequest.getIdPci()))
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_DELETE_AUTH_PURCHASE);
        else return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_DELETE_AUTH_PURCHASE);
    }

    @Override
    public GenericResponse activeDebitCardSecure(UpdateDebitCardSecureMWRequest request, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.ACTIVE_SECURE.getServiceURL();
        UpdateSecureMWResponse mwResponse = post(url, setHeaders(parameters), request, UpdateSecureMWResponse.class);
        if (mwResponse.getData().getIdPci() != null)
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_ACTIVE_ASSURANCE);
        else return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_ACTIVE_ASSURANCE);
    }

    @Override
    public DCDetailMWResponse detail(String personId, String cardId, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + String.format(DebitCardMiddlewareServices.DETAIL.getServiceURL(), personId, cardId);
        Header[] headers = {
                new BasicHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName()),
                new BasicHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode())),
                new BasicHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode()))
        };

        return get(url, headers, DCDetailMWResponse.class);
    }

    @Override
    public GenericResponse lockStatus(DCLockStatusMWRequest requestMW, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.UPDATE_LOCK_STATUS.getServiceURL();
        Header[] headers = setHeaders(parameters);
        DCLockStatusMWResponse mwResponse = patch(url, headers, requestMW, DCLockStatusMWResponse.class);
        if (mwResponse.getData().getPciId() != null)
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_UPDATE_STATUS_LOCK);
        else return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_UPDATE_STATUS_LOCK);
    }

    @Override
    public CreateAuthorizationOnlinePurchaseMWResponse createAuthorizationOnlinePurchase(CreateAuthorizationOnlinePurchaseMWRequest request, Map<String, String> parameter) throws IOException {
        String url = String.format("%s%s%s", middlewareConfig.getUrlBase(), ProjectNameMW.DEBIT_CARD_MANAGER.getName(), DebitCardMiddlewareServices.CREATE_AUTHORIZATION_ONLINE_PURCHASE.getServiceURL());

        Header[] headers = {
                new BasicHeader(HeadersMW.MW_CHA.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.APP_ID.getName(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName()),
                new BasicHeader(DeviceMW.DEVICE_ID.getCode(), parameter.get(DeviceMW.DEVICE_ID.getCode())),
                new BasicHeader(DeviceMW.DEVICE_IP.getCode(), parameter.get(DeviceMW.DEVICE_IP.getCode()))
        };
        return patch(url, headers, request, CreateAuthorizationOnlinePurchaseMWResponse.class);
    }

    @Override
    public GenericResponse modifyAccountsOrder(DCAccountsOrderMWRequest requestMW, Map<String, String> parameters) throws IOException {
        String url = middlewareConfig.getUrlBase() + ProjectNameMW.DEBIT_CARD_MANAGER.getName() + DebitCardMiddlewareServices.MODIFY_ACCOUNTS_ORDER.getServiceURL();
        Header[] headers = setHeaders(parameters);
        DCAccountsOrderMWResponse response = patch(url, headers, requestMW, DCAccountsOrderMWResponse.class);
        if (response.getData().getPciId() != null) {
            return GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_MODIFY_ACCOUNTS_ORDER);
        } else {
            return GenericResponse.instance(DebitCardMiddlewareResponse.ERROR_MODIFY_ACCOUNTS_ORDER);
        }
    }
}
