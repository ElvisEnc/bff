package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.Device;
import bg.com.bo.bff.application.dtos.response.DeviceEnrollmentResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.responses.login.DeviceEnrollmentMWResponse;

import java.io.IOException;

public interface IDeviceEnrollmentProvider {
    ClientToken generateToken() throws IOException;

    DeviceEnrollmentMWResponse makeValidateDevice(Device device, String token);
}
