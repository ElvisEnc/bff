package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import jakarta.validation.Valid;

import java.io.IOException;

public interface IUserService {
    GenericResponse changePassword(String personId, String ip, String deviceId, String userDeviceId, String rolePersonId, ChangePasswordRequest changePasswordRequest) throws IOException;
    ContactResponse getContactInfo();
}
