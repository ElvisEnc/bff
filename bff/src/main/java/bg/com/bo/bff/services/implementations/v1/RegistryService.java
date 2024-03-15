package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.dtos.response.RegistryResponse;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.enums.response.GenericControllerErrorResponse;
import bg.com.bo.bff.commons.enums.response.RegistryControllerErrorResponse;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.models.UserEncryptionKeys;
import bg.com.bo.bff.providers.interfaces.IEncryptionProvider;
import bg.com.bo.bff.providers.interfaces.ILoginAGNProvider;
import bg.com.bo.bff.services.interfaces.IRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

@Service
public class RegistryService implements IRegistryService {
    private final ILoginAGNProvider loginAGNProvider;
    private final IEncryptionProvider encryptionProvider;

    @Autowired
    public RegistryService(ILoginAGNProvider loginAGNProvider, IEncryptionProvider encryptionProvider) {
        this.loginAGNProvider = loginAGNProvider;
        this.encryptionProvider = encryptionProvider;
    }

    public RegistryResponse registerByMigration(RegistryRequest registryRequest) throws NoSuchAlgorithmException {
        Boolean authenticationResult = loginAGNProvider.login(registryRequest);

        if (authenticationResult) {
            KeyPair keyPair = encryptionProvider.createKeys();
            String appPublicKey = CipherUtils.encodeKeyToBase64(keyPair.getPublic());
            String appPrivateKey = CipherUtils.encodeKeyToBase64(keyPair.getPrivate());
            UserEncryptionKeys userEncryptionKeys = new UserEncryptionKeys(appPublicKey, appPrivateKey, registryRequest.getUserKey());

            Boolean registerResult = loginAGNProvider.registerDevice(registryRequest, userEncryptionKeys);

            if (registerResult) {
                RegistryResponse response = new RegistryResponse();
                response.setAppKey(userEncryptionKeys.getAppPublicKey());
                response.setPersonId(registryRequest.getCredentials().getPersonId());
                return response;
            }
            throw new HandledException(RegistryControllerErrorResponse.INVALID_REGISTER);
        }
        throw new HandledException(GenericControllerErrorResponse.UNAUTHORIZED);
    }
}
