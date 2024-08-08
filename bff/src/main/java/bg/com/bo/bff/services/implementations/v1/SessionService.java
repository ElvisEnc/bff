package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.services.interfaces.ISessionService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class SessionService implements ISessionService {
    private final CacheManager cacheManager;

    public SessionService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public boolean isOnBlacklist(Object token) {
        return get(CacheConstants.TOKEN_BLACKLIST, token) != null;
    }

    private <T> T get(String cacheName, Object key) {
        Cache cache = getCache(cacheName);

        Object result = cache.get(key);
        if (result != null)
            return (T) result;
        return null;
    }

    private Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null)
            throw new NotImplementedException();
        return cache;
    }

    public void blacklist(String key) {
        Cache cache = getCache(CacheConstants.TOKEN_BLACKLIST);
        cache.putIfAbsent(key, key);
    }
}
