package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.onboarding.manager.OnboardingManagerResponse;
import bg.com.bo.bff.mappings.providers.onboarding.manager.IOnboardingManagerMapper;
import bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw.ListDevicesMWResponse;
import bg.com.bo.bff.providers.interfaces.IOnboardingManagerProvider;
import bg.com.bo.bff.services.interfaces.IOnboardingManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OnboardingManagerService implements IOnboardingManagerService {
    private final IOnboardingManagerProvider provider;
    private final IOnboardingManagerMapper mapper;

    @Autowired
    public OnboardingManagerService(IOnboardingManagerProvider provider, IOnboardingManagerMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public List<OnboardingManagerResponse> getAllDevices(int personId) throws IOException {

        ListDevicesMWResponse mwResponse = provider.listDeviceOnboardingManager(personId);
        return new ArrayList<>(mapper.convertResponse(mwResponse));

    }

    @Override
    public GenericResponse disableDevice(int personId, String deviseId) throws IOException {
        return provider.disableDevice(personId, deviseId);
    }
}
