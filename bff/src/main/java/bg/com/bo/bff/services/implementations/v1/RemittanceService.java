package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.mappings.providers.remittance.IRemittanceMapper;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.GeneralParametersMWRequest;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ListGeneralParametersMWResponse;
import bg.com.bo.bff.providers.interfaces.IRemittanceProvider;
import bg.com.bo.bff.services.interfaces.IRemittanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
}
