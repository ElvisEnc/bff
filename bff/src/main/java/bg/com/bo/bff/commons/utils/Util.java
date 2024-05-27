package bg.com.bo.bff.commons.utils;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.Currency;
import bg.com.bo.bff.providers.dtos.responses.DynamicAppError;
import bg.com.bo.bff.providers.dtos.responses.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.responses.ApiNetErrorResponse;
import bg.com.bo.bff.providers.dtos.responses.ErrorDetailResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.*;
import java.util.regex.Pattern;

public class Util {
    private static final ObjectMapper objectMapper = createObjectMapper();
    private static final Logger LOGGER = LogManager.getLogger(Util.class.getName());
    private static final Map<String, Currency> currencyMap = new HashMap<>();
    private static final Map<String, Map<String, String>> schemeNameMap = new HashMap<>();

    static {
        currencyMap.put("068", Currency.BOB);
        currencyMap.put("978", Currency.EUR);
        currencyMap.put("840", Currency.USD);
        currencyMap.put("1000", Currency.UFV);

        Map<String, String> ownMap = new HashMap<>();
        ownMap.put("owner", "personId");
        ownMap.put("debtor", "accountId");
        ownMap.put("creditor", "accountId");
        schemeNameMap.put("own", ownMap);

        Map<String, String> achMap = new HashMap<>();
        achMap.put("owner", "PersonId");
        achMap.put("debtor", "AccountId");
        achMap.put("creditor", "TrustListAccountId");
        schemeNameMap.put("ach", achMap);
    }

    private Util() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }


    public static boolean validateUserAgents(List<String> userAgentsList, String userAgent) {
        boolean match = false;
        Iterator var3 = userAgentsList.iterator();

        while (var3.hasNext()) {
            String agent = (String) var3.next();
            if (agent.equals("*") || Pattern.matches(".*" + agent + ".*", userAgent)) {
                match = true;
                break;
            }
        }

        return match;
    }

    public static boolean isValidJSON(final String json) {
        boolean valid = true;

        try {
            objectMapper.readTree(json);
        } catch (IOException var3) {
            valid = false;
        }

        return valid;
    }

    public static String getExceptionMsg(Exception ex) {
        return ex.getCause() == null ? ex.getMessage() : ex.getCause().getMessage();
    }

    public static <T> T stringToObject(String json, Class<T> clazz) throws IOException {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException var3) {
            throw new IOException("Error String to Object");
        }
    }

    public static String objectToString(Object data) throws IOException {
        return objectToString(data, false);
    }

    public static String objectToString(Object data, boolean pretty) throws IOException {
        try {
            ObjectWriter ow = objectMapper.writer();
            if (pretty) {
                ow = objectMapper.writer().withDefaultPrettyPrinter();
            }

            return ow.writeValueAsString(data);
        } catch (JsonProcessingException var3) {
            throw new IOException("Error Object to String");
        }
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    public static String encodeSha512(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] bytes = text.getBytes();
            byte[] hashBytes = digest.digest(bytes);
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error obtaining hash algorithm");
        }
    }

    public static String getPayload(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try (InputStream inputStream = request.getInputStream()) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        }
        return stringBuilder.toString();
    }

    public static String formatDate(String dateString) {
        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate date = LocalDate.parse(dateString, formatter);
                return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            } catch (DateTimeParseException e) {
                LOGGER.error(e);
            }
        }
        return dateString;
    }

    public static AppError mapProviderError(String jsonResponse) throws IOException {
        ApiErrorResponse response = Util.stringToObject(jsonResponse, ApiErrorResponse.class);
        List<ErrorDetailResponse> listError = response.getErrorDetailResponse();
        ErrorDetailResponse errorDetail = listError.get(0);
        String providerErrorCode = errorDetail.getCode();
        return AppError.findByCode(providerErrorCode);
    }

    public static DynamicAppError mapNetProviderError(String jsonResponse) throws IOException {
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Error");
        ApiNetErrorResponse providerResponse = Util.stringToObject(jsonResponse, ApiNetErrorResponse.class);
        return new DynamicAppError(response.getStatus(), providerResponse.getCodigoError(), providerResponse.getMensaje());
    }

    public static byte[] getEncodedBytes(String data) {
        return data.getBytes(StandardCharsets.UTF_8);
    }

    public static String getStringFromEncodedBytes(byte[] decryptedData) {
        if (decryptedData == null) {
            LOGGER.error("decryptedData is null");
            throw new GenericException("Decrypted data cannot be null", AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    public static String encodeByteArrayToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static String decodeBase64ToString(String data) {
        if (data == null || data.isEmpty())
            throw new GenericException("Data cannot be null or empty", AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        byte[] decodeBytes;
        try {
            decodeBytes = Base64.getDecoder().decode(data);
        } catch (Exception e) {
            LOGGER.error("Invalid Base64 input: {}", data, e);
            throw new GenericException("Invalid Base64", AppError.DEFAULT.getHttpCode(), AppError.DEFAULT.getCode());
        }
        return getStringFromEncodedBytes(decodeBytes);
    }

    public static String convertCurrency(String currencyCode) {
        Currency currencyType = currencyMap.get(currencyCode);
        if (currencyType != null) {
            return currencyType.getCode();
        } else {
            return "10000";
        }
    }

    public static String getTransferSchemeName(String type, String key) {
        return schemeNameMap.getOrDefault(type, new HashMap<>()).getOrDefault(key, key);
    }
}
