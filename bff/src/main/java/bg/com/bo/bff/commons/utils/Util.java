package bg.com.bo.bff.commons.utils;

import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.providers.dtos.responses.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.responses.ErrorDetailResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class Util {
    private static final ObjectMapper objectMapper = createObjectMapper();

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

    public static AppError mapProviderError(String jsonResponse) throws IOException {
        ApiErrorResponse response = Util.stringToObject(jsonResponse, ApiErrorResponse.class);
        List<ErrorDetailResponse> listError = response.getErrorDetailResponse();
        ErrorDetailResponse errorDetail = listError.get(0);
        String providerErrorCode = errorDetail.getCode();
        switch (providerErrorCode) {
            case "MDWLM-009":
                return AppError.MDWLM_009;
            case "MDWLM-010":
                return AppError.MDWLM_010;
            case "MDWLM-011":
                return AppError.MDWLM_011;
            case "MDWLM-012":
                return AppError.MDWLM_012;
            case "MDWLM-013":
                return AppError.MDWLM_013;
            case "MDWLM-018":
                return AppError.MDWLM_018;
            case "MDWLM-019":
                return AppError.MDWLM_019;
            case "MDWLM-020":
                return AppError.MDWLM_020;
            case "MDWLM-23":
                return AppError.MDWLM_23;
            case "MDWLM-24":
                return AppError.MDWLM_24;
            case "MDWLM-25":
                return AppError.MDWLM_25;
            case "MDWRLIB-0003":
                return AppError.MDWRLIB_0003;
            case "MDWPGL-500", "MDWRLIB-0001", "MDWRLIB-0011", "MDWRLIB-0012", "MDWPGL-404", "MDWRLIB-0009", "MDWPGL-405":
                return AppError.DEFAULT;
            case "MDWPGL-400":
                return AppError.MDWPGL_400;
            case "MDWACM-008":
                return AppError.MDWACM_008;
            default:
                return AppError.DEFAULT;
        }
    }
}
