package bg.com.bo.bff.providers.models.middleware;

import bg.com.bo.bff.application.config.request.tracing.HeadersData;
import bg.com.bo.bff.commons.enums.config.provider.CanalMW;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.utils.Headers;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.*;

@Getter
@AllArgsConstructor
public enum HeadersMW {
    SECRET("Secret"),
    AUTH("Authorization"),
    MW_CHANNEL("middleware-channel"),
    TOPAZ_CHANNEL("topaz-channel"),
    APP_ID("application-id"),
    CONTENT_TYPE("Content-Type"),
    UTF_8("UTF-8"),
    APP_JSON("application/json"),
    DEVICE_ID("device-id"),
    DEVICE_IP("device-ip"),
    REQUEST_ID("X-Request-ID"),
    KONG_REQUEST_ID("X-Kong-Request-Id");

    private final String name;

    public static Header[] getMWIdentificationChannelHeaders() {
        return new Header[]{
                new BasicHeader(DeviceMW.MIDDLEWARE_CHANNEL.getCode(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(DeviceMW.APPLICATION_ID.getCode(), CanalMW.GANAMOVIL.getCanal())};
    }

    public static Header[] getDeviceHeaders(Map<String, String> parameters) {
        return new Header[]{
                new BasicHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode())),
                new BasicHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode())),
                new BasicHeader(DeviceMW.DEVICE_NAME.getCode(), parameters.get(DeviceMW.DEVICE_NAME.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_X.getCode(), parameters.get(DeviceMW.GEO_POSITION_X.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_Y.getCode(), parameters.get(DeviceMW.GEO_POSITION_Y.getCode())),
                new BasicHeader(DeviceMW.APP_VERSION.getCode(), parameters.get(DeviceMW.APP_VERSION.getCode()))};
    }

    public static Header[] getDefaultHeaders(HttpServletRequest request) {
        HeadersData headersData = HeadersData.buildDeviceData(request);
        String traceId = request.getHeader(REQUEST_ID.getName());
        Map<String, String> parameters = Headers.getParameter(headersData);
        if (traceId != null && !parameters.containsKey(REQUEST_ID.getName()))
            parameters.put(REQUEST_ID.getName(), traceId);
        return getDefaultHeaders(parameters);
    }

    public static Header[] getDefaultHeaders(Map<String, String> parameters) {
        return getHeaders(getDeviceHeaders(parameters),
                getMWIdentificationChannelHeaders(),
                getRequestIdHeaders(parameters),
                new Header[]{new BasicHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName())});
    }

    private static Header[] getRequestIdHeaders(Map<String, String> parameters) {
        String requestId = parameters.get(HeadersMW.REQUEST_ID.getName());
        Header[] requestIdHeader;
        if (requestId != null && !requestId.isBlank())
            requestIdHeader = new Header[]{
                    new BasicHeader(HeadersMW.REQUEST_ID.getName(), parameters.get(HeadersMW.REQUEST_ID.getName()))};
        else
            requestIdHeader = new Header[]{}; return requestIdHeader;
    }

    public static Header[] getHeaders(Header[]... headers) {
        Map<String, String> baseArray = new HashMap<>();

        for (Header[] headersArray : headers)
            for (Header header : headersArray)
                baseArray.putIfAbsent(header.getName(), header.getValue());

        Header[] result = new Header[baseArray.size()];

        int i = 0;
        for (Map.Entry<String, String> header : baseArray.entrySet()) {
            result[i] = new BasicHeader(header.getKey(), header.getValue());
            i++;
        }

        return result;
    }
}
