package bg.com.bo.bff.services.interfaces;

public interface ISessionService {
    boolean isOnBlacklist(Object token);

    void blacklist(String key);
}
