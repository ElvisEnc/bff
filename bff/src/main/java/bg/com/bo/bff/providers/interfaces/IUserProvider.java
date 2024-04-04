package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;

public interface IUserProvider {
    GenericResponse changePassword(String personId, ChangePasswordRequest changePasswordRequest);
}
