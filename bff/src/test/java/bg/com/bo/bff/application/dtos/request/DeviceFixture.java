package bg.com.bo.bff.application.dtos.request;

public class DeviceFixture {
    public static Device withDefault() {
        Device device = new Device();
        device.setBoot("testData");
        device.setUniqueId("testData");
        device.setSoCodeName("testData");
        device.setSystemName("testData");
        device.setSystemVersion("testData");
        device.setSystemBuildId("testData");
        device.setUserAgent("testData");
        device.setFirstInstallTime("testData");
        device.setDebug("testData");
        device.setEmulator("testData");
        device.setGeoPositionX("testData");
        device.setGeoPositionY("testData");
        return device;
    }
}