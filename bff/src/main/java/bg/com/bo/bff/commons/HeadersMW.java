package bg.com.bo.bff.commons;

import bg.com.bo.bff.commons.enums.CanalMW;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.enums.Headers;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.Map;

public class HeadersMW {
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
                new BasicHeader(Headers.CONTENT_TYPE.getName(), Headers.APP_JSON.getName())};
    }
}
