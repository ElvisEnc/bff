package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import jakarta.validation.Valid;

import java.io.IOException;

public interface IUserService {
    GenericResponse changePassword(String personId, String ip, String deviceId, String deviceUniqueId, String rolePersonId, ChangePasswordRequest changePasswordRequest) throws IOException;
}
