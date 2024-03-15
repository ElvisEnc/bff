package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.models.UserEncryptionKeys;

public interface ILoginAGNProvider {
    Boolean login(RegistryRequest loginAGNBaseRequest);

    Boolean registerDevice(RegistryRequest registryRequest, UserEncryptionKeys userEncryptionKeys);
}
