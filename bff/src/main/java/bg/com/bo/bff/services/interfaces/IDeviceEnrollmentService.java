package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.Device;
import bg.com.bo.bff.application.dtos.response.DeviceEnrollmentResponse;

import java.io.IOException;
import java.util.Map;

public interface IDeviceEnrollmentService {
    DeviceEnrollmentResponse validation( Map<String, String> parameter) throws IOException;
}
