package bg.com.bo.bff.commons.utils;

import bg.com.bo.bff.commons.enums.DeviceMW;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.Collection;
import java.util.Enumeration;
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

    public static Map<String, String> getHeaders(HttpServletRequest httpServletRequest) {
        Map<String, String> requestHeaders = new HashMap<>();
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                requestHeaders.put(name, httpServletRequest.getHeader(name));
            }
        }
        return requestHeaders;
    }

    public static Map<String, String> getHeaders(HttpServletResponse httpServletResponse) {
        Map<String, String> responseHeaders = new HashMap<>();
        Collection<String> headersResponse = httpServletResponse.getHeaderNames();
        if (headersResponse != null)
            for (String name : headersResponse)
                responseHeaders.put(name, httpServletResponse.getHeader(name));
        return responseHeaders;
    }
}
