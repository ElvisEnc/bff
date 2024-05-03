package bg.com.bo.bff.commons.utils;

import bg.com.bo.bff.commons.enums.DeviceMW;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class Headers {
    public static Map<String, String> getParameter(HttpServletRequest request, String ...headers) {
        Map<String, String> parameters = new HashMap<>();

        parameters.put(DeviceMW.DEVICE_ID.getCode(), headers[0]);
        parameters.put(DeviceMW.DEVICE_NAME.getCode(), headers[1]);
        parameters.put(DeviceMW.GEO_POSITION_X.getCode(), headers[2]);
        parameters.put(DeviceMW.GEO_POSITION_Y.getCode(), headers[3]);
        parameters.put(DeviceMW.APP_VERSION.getCode(), headers[4]);
        parameters.put(DeviceMW.DEVICE_IP.getCode(), request.getRemoteAddr());

        return parameters;
    }
}
