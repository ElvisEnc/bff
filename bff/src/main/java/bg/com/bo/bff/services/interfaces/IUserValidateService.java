package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.model.UserValidateRequest;

public interface IUserValidateService {
    Object validarUsuario(UserValidateRequest request);
}
