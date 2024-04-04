package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;

public interface IUserService {
    GenericResponse changePassword(String personId, ChangePasswordRequest changePasswordRequest);
}
