package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.Device;
import bg.com.bo.bff.application.dtos.response.DeviceEnrollmentResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.responses.login.DeviceEnrollmentMWResponse;
import bg.com.bo.bff.providers.interfaces.IDeviceEnrollmentProvider;
import bg.com.bo.bff.services.interfaces.IDeviceEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class DeviceEnrollmentService implements IDeviceEnrollmentService {

    @Autowired
    private IDeviceEnrollmentProvider iDeviceEnrollmentProvider;

    public DeviceEnrollmentResponse validation(Device device) throws IOException {
        ClientToken clientToken = iDeviceEnrollmentProvider.generateToken();
        String token = clientToken.getAccessToken();
        DeviceEnrollmentMWResponse deviceEnrollmentResponse = iDeviceEnrollmentProvider.makeValidateDevice(device, token);

        String enrolled = deviceEnrollmentResponse.getStatusCode();
        DeviceEnrollmentResponse response = new DeviceEnrollmentResponse();
        if (Objects.equals(enrolled, "ENROLLED")) {
            response.setPersonId(deviceEnrollmentResponse.getPersonId());
            response.setStatusCode(1);
        } else
            response.setStatusCode(2);
        return response;
    }
}
