package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.remittance.DepositRemittanceRequest;
import bg.com.bo.bff.application.dtos.request.remittance.DepositRemittanceWURequest;
import bg.com.bo.bff.application.dtos.request.remittance.UpdateWURemittanceRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.remittance.CheckRemittanceResponse;
import bg.com.bo.bff.application.dtos.response.remittance.DepositRemittanceResponse;
import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.application.dtos.response.remittance.MoneyOrderSentResponse;
import bg.com.bo.bff.application.dtos.response.remittance.UpdateWURemittanceResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.user.AppCodeResponseNet;
import bg.com.bo.bff.mappings.providers.remittance.IRemittanceMapper;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.CheckRemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.ConsultWURemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.DepositRemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.DepositRemittanceWUMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.GeneralParametersMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.MoneyOrderSentMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.UpdateWURemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.ValidateAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.CheckRemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.DepositRemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ListGeneralParametersMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.MoneyOrderSentMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.UpdateWURemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ValidateAccountMWResponse;
import bg.com.bo.bff.providers.interfaces.IRemittanceProvider;
import bg.com.bo.bff.providers.models.enums.middleware.remittance.RemittanceMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.remittance.RemittanceMiddlewareResponse;
import bg.com.bo.bff.services.interfaces.IRemittanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RemittanceService implements IRemittanceService {
    private final IRemittanceProvider provider;
    private final IRemittanceMapper mapper;

    @Autowired
    private RemittanceService self;

    public RemittanceService(IRemittanceProvider provider, IRemittanceMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public ListGeneralParametersResponse getGeneralParameters(String personId) throws IOException {
        return self.getGeneralParametersData(personId);
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.REMITTANCE_PARAMETERS, key = "#personId")})
    public ListGeneralParametersResponse getGeneralParametersData(String personId) throws IOException {
        GeneralParametersMWRequest mwRequest = mapper.mapperRequest(personId);
        ListGeneralParametersMWResponse mwResponse = provider.getGeneralParameters(mwRequest);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public GenericResponse validateAccount(String personId, String accountId) throws IOException {
        ValidateAccountMWRequest mwRequest = mapper.mapperRequest(personId, accountId);
        ValidateAccountMWResponse mwResponse = provider.validateAccount(mwRequest);
        if (mwResponse.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return GenericResponse.instance(RemittanceMiddlewareResponse.ACCOUNT_ENABLED);
        }
        throw new GenericException(RemittanceMiddlewareError.RM031);
    }

    @Override
    public List<MoneyOrderSentResponse> getMoneyOrdersSent(String personId) throws IOException {
        MoneyOrderSentMWRequest mwRequest = mapper.mapperRequestOrders(personId);
        MoneyOrderSentMWResponse mwResponse = provider.getMoneyOrdersSent(mwRequest);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }

    @Override
    public List<CheckRemittanceResponse> checkRemittance(String personId, String remittanceId) throws IOException {
        CheckRemittanceMWRequest mwRequest = mapper.mapperRequestRemittance(personId, remittanceId);
        CheckRemittanceMWResponse mwResponse = provider.checkRemittance(mwRequest);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }

    @Override
    public List<DepositRemittanceResponse> depositRemittance(
            String personId, String remittanceId, DepositRemittanceRequest request
    ) throws IOException {
        DepositRemittanceMWRequest mwRequest = mapper.mapperRequestDeposit(personId, remittanceId, request);
        DepositRemittanceMWResponse mwResponse = provider.depositRemittance(mwRequest);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }

    @Override
    public List<DepositRemittanceResponse> depositRemittanceWU(
            String personId, String remittanceId, DepositRemittanceWURequest request
    ) throws IOException {
        DepositRemittanceWUMWRequest mwRequest = mapper.mapperRequestDepositWU(personId, remittanceId, request);
        DepositRemittanceMWResponse mwResponse = provider.depositRemittanceWU(mwRequest);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }

    @Override
    public List<CheckRemittanceResponse> consultWURemittance(
            String personId, String remittanceId, String jtsOidAccount
    ) throws IOException {
        ConsultWURemittanceMWRequest mwRequest = mapper.mapperRequestRemittanceWU(personId, remittanceId, jtsOidAccount);
        CheckRemittanceMWResponse mwResponse = provider.consultWURemittance(mwRequest);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }

    @Override
    public UpdateWURemittanceResponse updateWURemittance(String personId, String consultId, UpdateWURemittanceRequest request)
            throws IOException {
        UpdateWURemittanceMWRequest mwRequest = mapper.mapperRequestRemittanceWUUpdate(personId, consultId, request);
        UpdateWURemittanceMWResponse mwResponse = provider.updateWURemittance(mwRequest);
        return mapper.convertResponse(mwResponse);
    }

}
