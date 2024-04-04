package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.providers.interfaces.IUserProvider;
import bg.com.bo.bff.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private IUserProvider userProvider;

    @Autowired
    public UserService(IUserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Override
    public GenericResponse changePassword(String personId, ChangePasswordRequest changePasswordRequest) {
        return userProvider.changePassword(personId, changePasswordRequest);
    }
}
