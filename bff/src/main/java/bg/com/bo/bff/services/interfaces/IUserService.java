package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.Map;

public interface IUserService {
    GenericResponse changePassword(String personId, String ip, String deviceId, String userDeviceId, String rolePersonId, ChangePasswordRequest changePasswordRequest) throws IOException;

    ContactResponse getContactInfo();

    PersonalResponse getPersonalInformation(String personId, Map<String, String> parameter) throws IOException;
}
