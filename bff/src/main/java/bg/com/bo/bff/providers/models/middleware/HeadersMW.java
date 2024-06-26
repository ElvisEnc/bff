package bg.com.bo.bff.providers.models.middleware;

import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.DeviceMW;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum HeadersMW {
    SEC("Secret"),
    AUT("Authorization"),
    MW_CHA("middleware-channel"),
    TOP_CHA("topaz-channel"),
    APP_ID("application-id"),
    CONTENT_TYPE("Content-Type"),
    APP_JSON("application/json"),
    DEVICE_ID("device-id"),
    DEVICE_IP("device-ip"),
    REQUEST_ID("X-Request-ID");

    private final String name;

    public static Header[] getHeadersMW(Map<String, String> parameters) {
        return new Header[]{
                new BasicHeader(DeviceMW.MIDDLEWARE_CHANNEL.getCode(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(DeviceMW.APPLICATION_ID.getCode(), CanalMW.GANAMOVIL.getCanal()),
                new BasicHeader(DeviceMW.DEVICE_ID.getCode(), parameters.get(DeviceMW.DEVICE_ID.getCode())),
                new BasicHeader(DeviceMW.DEVICE_IP.getCode(), parameters.get(DeviceMW.DEVICE_IP.getCode())),
                new BasicHeader(DeviceMW.DEVICE_NAME.getCode(), parameters.get(DeviceMW.DEVICE_NAME.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_X.getCode(), parameters.get(DeviceMW.GEO_POSITION_X.getCode())),
                new BasicHeader(DeviceMW.GEO_POSITION_Y.getCode(), parameters.get(DeviceMW.GEO_POSITION_Y.getCode())),
                new BasicHeader(DeviceMW.APP_VERSION.getCode(), parameters.get(DeviceMW.APP_VERSION.getCode())),
                new BasicHeader(HeadersMW.CONTENT_TYPE.getName(), HeadersMW.APP_JSON.getName())};
    }
}
