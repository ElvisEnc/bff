package bg.com.bo.bff.application.config.fixtures;

import bg.com.bo.bff.models.UserData;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtAccess;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtPayload;

import java.util.List;

public class JwtAuthenticationFilterFixture {
    public static UserData withDefaultUserData() {
        UserData userData = new UserData();
        userData.setPersonId("1");
        userData.setSid("1");
        return userData;
    }

    public static JwtAccess withDefaultJwtAccess() {
        JwtAccess access = new JwtAccess();
        JwtPayload payload = new JwtPayload();
        String role = "LOGGED";
        payload.setRoles(List.of(role));
        access.setPayload(payload);

        return access;
    }
}
