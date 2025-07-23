package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.own.account.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.own.account.TransactionLimitsResponse;
import bg.com.bo.bff.application.dtos.response.own.account.OwnAccountsResponse;
import bg.com.bo.bff.mappings.providers.account.IOwnAccountsMapper;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.OwnAccountsListMWResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.TransactionLimitsMWResponse;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.UpdateTransactionLimitMWRequest;
import bg.com.bo.bff.providers.interfaces.IOwnAccountsProvider;
import bg.com.bo.bff.services.interfaces.IAccountService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class OwnAccountService implements IAccountService {
    private final IOwnAccountsProvider ownAccountsProvider;
    private final IOwnAccountsMapper mapper;

    public OwnAccountService(IOwnAccountsProvider ownAccountsProvider, IOwnAccountsMapper mapper) {
        this.ownAccountsProvider = ownAccountsProvider;
        this.mapper = mapper;
    }

    public List<OwnAccountsResponse> getAccounts(String personId, String userDeviceId, Map<String, String> parameter) throws IOException {
        OwnAccountsListMWResponse mwResponse = ownAccountsProvider.getAccounts(personId, userDeviceId, parameter);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public GenericResponse updateTransactionLimit(String personId, String accountId, UpdateTransactionLimitRequest request, Map<String, String> parameter) throws IOException {
        UpdateTransactionLimitMWRequest requestMW = mapper.mapperRequest(request);
        return ownAccountsProvider.updateTransactionLimit(personId, accountId, requestMW, parameter);
    }

    @Override
    public TransactionLimitsResponse getTransactionLimit(String personId, String accountId, Map<String, String> parameter) throws IOException {
        TransactionLimitsMWResponse mwResponse = ownAccountsProvider.getTransactionLimit(personId, accountId, parameter);
        return mapper.convertResponse(mwResponse);
    }
}
