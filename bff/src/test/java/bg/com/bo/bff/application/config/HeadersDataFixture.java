package bg.com.bo.bff.application.config;

import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import org.springframework.mock.web.MockHttpServletRequest;

public class HeadersDataFixture {
    private static final String DEVICE_ID = "42ebffbd7c30307d";
    private static final String DEVICE_IP = "127.0.0.1";
    private static final String DEVICE_NAME = "Android";
    private static final String GEO_POSITION_X = "12.265656";
    private static final String GEO_POSITION_Y = "12.454545";
    private static final String APP_VERSION = "1.0.0";

    public static MockHttpServletRequest getMockHttpServletRequest() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader(DeviceMW.DEVICE_ID.getCode(), DEVICE_ID);
        mockRequest.addHeader(DeviceMW.DEVICE_NAME.getCode(), DEVICE_NAME);
        mockRequest.addHeader(DeviceMW.GEO_POSITION_X.getCode(), GEO_POSITION_X);
        mockRequest.addHeader(DeviceMW.GEO_POSITION_Y.getCode(), GEO_POSITION_Y);
        mockRequest.addHeader(DeviceMW.APP_VERSION.getCode(), APP_VERSION);
        mockRequest.setRemoteAddr(DEVICE_IP);
        return mockRequest;
    }
}
