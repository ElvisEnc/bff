package bg.com.bo.bff.services.implementations.v1.fixtures;

import java.util.HashMap;
import java.util.Map;

public class ErrorServiceTestFixture {
    public static Map<String, Object> withForbidden() {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", "2024-10-24T18:42:39.190+00:00");
        error.put("status", 403);
        error.put("error", "Forbidden");
        error.put("path", "/staging/ganamovil-bff/api/v1/destination-accounts/account-types");
        return error;
    }

    public static Map<String, Object> withBadRequest() {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", "2024-10-24T18:42:39.190+00:00");
        error.put("status", 400);
        error.put("error", "Bad Request");
        error.put("path", "/staging/ganamovil-bff/api/v1/destination-accounts/account-types");
        return error;
    }

    public static Map<String, Object> withUnauthorized() {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", "2024-10-24T18:42:39.190+00:00");
        error.put("status", 401);
        error.put("error", "Unauthorized");
        error.put("path", "/staging/ganamovil-bff/api/v1/destination-accounts/account-types");
        return error;
    }
}
