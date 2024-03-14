package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.Device;
import bg.com.bo.bff.application.dtos.response.DeviceEnrollmentResponse;

import java.io.IOException;

public interface IDeviceEnrollmentService {
    DeviceEnrollmentResponse validation(Device device) throws IOException;
}
