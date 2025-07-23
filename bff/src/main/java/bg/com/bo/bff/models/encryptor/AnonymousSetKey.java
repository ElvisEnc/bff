package bg.com.bo.bff.models.encryptor;

import lombok.Getter;

@Getter
public class AnonymousSetKey {
    private final String prefix;
    private final String id;
    private final Long expiration;

    public AnonymousSetKey(String key) {
        String[] values = key.split(":");
        this.prefix = values[0];
        this.id = values[1];
        this.expiration = Long.parseLong(values[2]);
    }

    public String getValue() {
        return this.prefix + ":" + this.id;
    }

    public boolean isExpired(long expirationThreshold) {
        long keyTtlInMillis = expirationThreshold * 60 * 1000;
        return getExpiration() < System.currentTimeMillis() + keyTtlInMillis;
    }
}
