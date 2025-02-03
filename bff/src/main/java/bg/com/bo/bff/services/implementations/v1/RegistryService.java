package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.dtos.response.registry.BffHandshakeResponse;
import bg.com.bo.bff.application.dtos.response.registry.RegistryResponse;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.models.payload.encryption.EncryptionKey;
import bg.com.bo.bff.providers.interfaces.IAnonymousKeyProvider;
import bg.com.bo.bff.providers.models.enums.middleware.response.GenericControllerErrorResponse;
import bg.com.bo.bff.providers.models.enums.middleware.response.RegistryControllerErrorResponse;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.providers.dtos.request.encryption.EncryptInfo;
import bg.com.bo.bff.providers.dtos.response.encryption.UserEncryptionKeys;
import bg.com.bo.bff.providers.interfaces.ILoginAGNProvider;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import bg.com.bo.bff.services.interfaces.IRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

@Service
public class RegistryService implements IRegistryService {
    private final MiddlewareConfig mdwConfig;
    private final ILoginAGNProvider loginAGNProvider;
    private final IEncryptionService encryptionService;

    CacheManager cacheManager;

    @Autowired
    public RegistryService(ILoginAGNProvider loginAGNProvider, IEncryptionService encryptionProvider, CacheManager cacheManager, IAnonymousKeyProvider anonymousKeyProvider, MiddlewareConfig mdwConfig) {
        this.loginAGNProvider = loginAGNProvider;
        this.encryptionService = encryptionProvider;
        this.cacheManager = cacheManager;
        this.mdwConfig = mdwConfig;
    }

    @Override
    public RegistryResponse registerByMigration(RegistryRequest registryRequest) throws NoSuchAlgorithmException {
        Boolean authenticationResult = loginAGNProvider.login(registryRequest);

        if (authenticationResult) {
            String appPublicKey = mdwConfig.getPublicKey();
            String appPrivateKey = mdwConfig.getPrivateKey();
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

    @Override
    public BffHandshakeResponse handshake() {
        EncryptionKey key = this.encryptionService.getAnonymousKey();
        return BffHandshakeResponse.builder()
                .key(key.getId())
                .eki(key.getPublicKey())
                .expireAt(System.currentTimeMillis())
                .build();
    }
}
