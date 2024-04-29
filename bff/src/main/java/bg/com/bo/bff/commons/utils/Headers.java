package bg.com.bo.bff.commons.utils;

import bg.com.bo.bff.commons.enums.DeviceMW;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Headers {
    public static Map<String, String> getParameter(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            parameters.put(headerName, headerValue);
        }
        parameters.put(DeviceMW.DEVICE_IP.getCode(), request.getRemoteAddr());

        return parameters;
    }
}
