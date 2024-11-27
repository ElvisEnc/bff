package bg.com.bo.bff.commons.constants;

public final class Constants {

    private Constants() {
    }

    public static final String ISSUER = "BFF";
    public static final String USER_SESSION_ENCRYPT_INFO_HEADER = "Bff-EI";
    public static final String SESSION_ENCRYPT_KEY_HEADER = "Bff-SEK";
    public static final int PASSWORD_MIN_LENGTH = 7;
    public static final int PASSWORD_MAX_LENGTH = 15;
    public static final String ENCRYPTION_EXCLUDED_KEY_HEADER = "Bff-EEK";
}
