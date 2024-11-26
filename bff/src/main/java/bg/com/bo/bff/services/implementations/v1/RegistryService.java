package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.dtos.response.registry.RegistryResponse;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.providers.models.enums.middleware.response.GenericControllerErrorResponse;
import bg.com.bo.bff.providers.models.enums.middleware.response.RegistryControllerErrorResponse;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.providers.dtos.request.encryption.EncryptInfo;
import bg.com.bo.bff.providers.dtos.response.encryption.UserEncryptionKeys;
import bg.com.bo.bff.providers.interfaces.IEncryptionProvider;
import bg.com.bo.bff.providers.interfaces.ILoginAGNProvider;
import bg.com.bo.bff.services.interfaces.IRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

@Service
public class RegistryService implements IRegistryService {
    private final ILoginAGNProvider loginAGNProvider;
    private final IEncryptionProvider encryptionProvider;

    CacheManager cacheManager;

    @Autowired
    public RegistryService(ILoginAGNProvider loginAGNProvider, IEncryptionProvider encryptionProvider, CacheManager cacheManager) {
        this.loginAGNProvider = loginAGNProvider;
        this.encryptionProvider = encryptionProvider;
        this.cacheManager = cacheManager;
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
                EncryptInfo encryptInfo = new EncryptInfo();
                encryptInfo.setPersonId(registryRequest.getCredentials().getPersonId());
                encryptInfo.setUniqueId(registryRequest.getDeviceIdentificator().getUniqueId());
                Cache cache = cacheManager.getCache(CacheConstants.ENCRYPTION_KEYS_CACHE_NAME);
                if (cache != null)
                    cache.evict(encryptInfo);

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
