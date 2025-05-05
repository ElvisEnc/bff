package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.token.external.TokenAuthenticationRequestDto;
import bg.com.bo.bff.providers.interfaces.ITokenExternalProvider;
import bg.com.bo.bff.providers.models.external.services.interfaces.CryptoAssetsFeignClient;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Log4j2
public class TokenExternalProvider implements ITokenExternalProvider {

    private final CryptoAssetsFeignClient authClient;

    private final Map<String, ClientToken> tokenStore = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> expirationStore = new ConcurrentHashMap<>();

    @Override
    public synchronized ClientToken generateAccountAccessToken(TokenAuthenticationRequestDto tokenAuthenticationRequestDto) {
        String key = String.format("mdw_token_%s", tokenAuthenticationRequestDto.getUsername());

        ClientToken token = tokenStore.get(key);
        LocalDateTime expiration = expirationStore.get(key);

        if (token != null && expiration != null && expiration.isAfter(LocalDateTime.now())) {
            return token;
        }

        try {
            ClientToken newToken = authClient.getTokenAuthentication(tokenAuthenticationRequestDto);

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expireAt = now.plusSeconds(newToken.getExpiresIn());

            newToken.setRequiredAt(now.toString());
            newToken.setExpiredAt(expireAt.toString());

            tokenStore.put(key, newToken);
            expirationStore.put(key, expireAt);

            return newToken;
        } catch (Exception e) {
            log.error("Error al obtener token", e);
            throw new GenericException(DefaultMiddlewareError.MW_FAILURE);
        }
    }
}