package bg.com.bo.bff.mappings.implementations;

import bg.com.bo.bff.mappings.interfaces.IAuthMapper;
import bg.com.bo.bff.models.UserData;
import bg.com.bo.bff.models.jwt.JwtAccess;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper implements IAuthMapper {

    @Override
    public UserData convert(JwtAccess jwtAccess) {
        String personId = jwtAccess.getPayload().getPersonId();
        String sid = jwtAccess.getPayload().getSid();

        UserData userData = new UserData();
        userData.setPersonId(personId);
        userData.setSid(sid);
        return userData;
    }
}
