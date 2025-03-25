package bg.com.bo.bff.commons.utils;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.enums.transfer.Currency;
import bg.com.bo.bff.commons.enums.destination.account.DestinationAccountBG;
import bg.com.bo.bff.providers.dtos.response.generic.DynamicAppError;
import bg.com.bo.bff.providers.dtos.response.generic.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.ApiNetErrorResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorDetailResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final ObjectMapper objectMapper = createObjectMapper();
    private static final Logger LOGGER = LogManager.getLogger(Util.class.getName());
    private static final Map<String, Currency> currencyMap = new HashMap<>();
    private static final Map<String, Map<String, String>> schemeNameMap = new HashMap<>();
    private static final Pattern EIF_PATTERN = Pattern.compile("^\\D*(\\d+)");
    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

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
            LOGGER.error(String.format("%s=%s", "stringToObject", json));
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

    public static boolean isStringNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String getEIF(String eif) {
        Matcher match = EIF_PATTERN.matcher(eif);
        if (match.find()) {
            int digits = Integer.parseInt(match.group(1));
            return String.valueOf(digits);
        } else {
            return eif;
        }
    }

    public static String getBankType(String codeSegment, String walletSegment) {
        boolean isThirdBank = isThirdBank(codeSegment);
        boolean isWallet = walletSegment.length() == 8;

        if (!isThirdBank) {
            return DestinationAccountBG.ACH.getName();
        }
        return isWallet ? DestinationAccountBG.WALLET.getName() : DestinationAccountBG.THIRD.getName();
    }

    private static boolean isThirdBank(String codeSegment) {
        return "1919".equals(codeSegment) || "01919".equals(codeSegment)
                || "1018".equals(codeSegment) || "01018".equals(codeSegment)
                || "MLD1018".equals(codeSegment);
    }

    /**
     * Convert a string number without explicit decimals to a big decimal number with n decimals.
     * Example: "100" -> 1.00
     *
     * @param number   string number to convert.
     *                 The number is divided by 100 with n decimals.
     *                 The number is rounded up.
     * @param decimals number of decimals.
     * @return decimal  number.
     */
    public static BigDecimal convertToDecimal(String number, int decimals) {
        return new BigDecimal(number).divide(new BigDecimal(100), decimals, RoundingMode.HALF_UP);
    }

    public static String obfuscateCardNumber(String cardNumber) {
        if (cardNumber == null)
            return "**** **** **** ****";
        String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);
        return "**** **** **** " + lastFourDigits;
    }

    public static String sanitizeProjectName(String projectName) {
        return projectName.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static String formatterTime(String inputTime) {
        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS"),
                DateTimeFormatter.ofPattern("HH:mm:ss"),
                DateTimeFormatter.ofPattern("HH:mm")
        );
        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalTime time = LocalTime.parse(inputTime, formatter);
                return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            } catch (DateTimeParseException e) {
                LOGGER.debug("Unable to parse time with format: %s".formatted(formatter.toString()), e);
            }
        }
        return inputTime;
    }

    public static BigDecimal scaleToTwoDecimals(BigDecimal value) {
        if (value == null)
            return null;
        return value.setScale(2, RoundingMode.DOWN);
    }

    public static BigDecimal convertStringToBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            BigDecimal bigDecimalValue = new BigDecimal(value);
            return scaleToTwoDecimals(bigDecimalValue);
        } catch (NumberFormatException e) {
            LOGGER.debug("El valor otorgado no es numerico: %s".formatted(value), e);
            return null;
        }
    }

    public static Integer convertStringToInteger(String value) {
        try {
            return value != null ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            LOGGER.debug("El valor otorgado no es numerico: %s".formatted(value), e);
            return null;
        }
    }

    public static <T> void validate(T object) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append(", ");
            }
            throw new ConstraintViolationException(sb.toString(), violations);
        }
    }

    public static Long minutesToMillis(Long minutes) {
        return minutes * 60L * 1000L;
    }

    public static Long minutesToMillis(Integer minutes) {
        return minutesToMillis(Long.valueOf(minutes));
    }

    public static Integer tryParse(Object input) {
        try {
            return Integer.parseInt(input.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8").replaceAll("\\+", "%20"); // Replaces spaces with %20 instead of "+"
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding failed", e);
        }
    }

    public static String normalizeProductDescription(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String normalizedInput = input.replaceAll("\\s+", " ").trim().toUpperCase();
        String prefix = "CAJA DE AHORRO GANADOBLE";
        String description = "GANADOBLE";

        if (normalizedInput.startsWith(prefix)) {
            return description + normalizedInput.substring(prefix.length());
        }

        if (normalizedInput.contains(description)) {
            return description;
        }

        return input;
    }

    public static boolean IsDevLogConfigurationFile() {
        String logConfigurationFileByEnv = System.getenv("log4j.configurationFile");
        String logConfigurationFileByProperty = System.getProperty("log4j.configurationFile");
        String configurationFile = null;
        if (logConfigurationFileByEnv != null)
            configurationFile = logConfigurationFileByEnv;
        else if (logConfigurationFileByProperty != null)
            configurationFile = logConfigurationFileByProperty;

        if (configurationFile != null) {
            String configurationFileWithoutExtension = configurationFile.contains(".") ?
                    configurationFile.substring(0, configurationFile.lastIndexOf('.')) : configurationFile;
            if (configurationFileWithoutExtension.endsWith("-dev"))
                return true;
            else
                return false;
        } else
            return false;
    }

    public static String getStatusDebitCard(String status){
        switch (status) {
            case "S", "T", "Z": return "Pendiente";
            case "B": return "Bloqueada";
            case "C": return "Eliminada";
            case "A": return "Activo";
            default: return "Undefined";
        }
    }
}
