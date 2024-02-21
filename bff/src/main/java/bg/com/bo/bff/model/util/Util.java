package bg.com.bo.bff.model.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
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

        while(var3.hasNext()) {
            String agent = (String)var3.next();
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
}
