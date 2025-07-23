package bg.com.bo.bff.application.config.request.tracing;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class HeadersData {
    @NotBlank(message = "device-id not valid")
    @Size(min = 1, max = 500, message = "The device-id must be between 1 and 500 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9-]+", message = "device-id is not valid")
    private String deviceId;

    @NotBlank(message = "device-ip not valid")
    @Size(min = 1, max = 20, message = "The device-ip must be between 1 and 20 characters.")
    private String deviceIp;

    @NotBlank(message = "device-name cannot be empty")
    @Size(max = 100, message = "device-name cannot have more than 100 characters")
    private String deviceName;

    @NotBlank(message = "geo-position-x cannot be empty")
    @Size(max = 24, message = "geo-position-x cannot have more than 24 characters")
    @Pattern(regexp = "^(-?\\d+(\\.\\d+)?)$", message = "geo-position-x is not valid")
    private String geoPositionX;

    @NotBlank(message = "geo-position-y cannot be empty")
    @Size(max = 24, message = "geo-position-y cannot have more than 24 characters")
    @Pattern(regexp = "^(-?\\d+(\\.\\d+)?)$", message = "geo-position-y is not valid")
    private String geoPositionY;

    @NotBlank(message = "app-version cannot be empty")
    @Size(max = 20, message = "app-version cannot have more than 20 characters")
    private String appVersion;

    public static HeadersData buildDeviceData(HttpServletRequest requestHttp) {
        HeadersData headerData = new HeadersData();
        if (requestHttp.getHeaderNames() != null) {
            headerData.setDeviceId(requestHttp.getHeader(DeviceMW.DEVICE_ID.getCode()));
            headerData.setDeviceIp(requestHttp.getRemoteAddr());
            headerData.setDeviceName(requestHttp.getHeader(DeviceMW.DEVICE_NAME.getCode()));
            headerData.setGeoPositionX(requestHttp.getHeader(DeviceMW.GEO_POSITION_X.getCode()));
            headerData.setGeoPositionY(requestHttp.getHeader(DeviceMW.GEO_POSITION_Y.getCode()));
            headerData.setAppVersion(requestHttp.getHeader(DeviceMW.APP_VERSION.getCode()));
        }
        try {
            Util.validate(headerData);
            return headerData;
        } catch (Exception ex) {
            throw new GenericException(DefaultMiddlewareError.HEADER_DATA);
        }
    }
}
