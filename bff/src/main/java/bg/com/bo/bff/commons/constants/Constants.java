package bg.com.bo.bff.commons.constants;

public final class Constants {

    private Constants() {
    }

    public static final String ISSUER = "BFF";
    public static final String CERTS_CACHE_NAME = "certs";
    public static final String ENCRYPTION_KEYS_CACHE_NAME = "encryptionkeys";
    public static final String BFF_ENCODE_INFO_HEADER = "Bff-EI";
    public static final String SESSION_ENCRYPTED_KEY_HEADER = "Bff-SEK";
    public static final String ACCOUNTS_STATEMENTS = "accountstatements";
    public static final String DESTINATION_ACCOUNTS = "destinationaccounts";

    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 15;
}
