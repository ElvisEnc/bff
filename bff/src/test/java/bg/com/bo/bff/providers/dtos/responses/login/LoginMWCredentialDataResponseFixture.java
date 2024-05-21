package bg.com.bo.bff.providers.dtos.responses.login;
import java.util.Collections;

public class LoginMWCredentialDataResponseFixture {
        public static LoginMWCredentialDataResponse withDefault(){
            LoginMWCredentialDataResponse dataResponse = new LoginMWCredentialDataResponse();
            dataResponse.setSecurityImage("defaultSecurityImage");
            dataResponse.setSecondFactor("defaultSecondFactor");
            dataResponse.setKeyChange("defaultKeyChange");
            dataResponse.setKeyChangeMessage("defaultKeyChangeMessage");
            dataResponse.setUserDeviceId(123);
            dataResponse.setRoleList(Collections.singletonList(LoginMWCredentialDataResponseFixture.roleWithDefault()));
            dataResponse.setLastConnectionDate("2024-05-16");
            dataResponse.setCodError("0000");
            dataResponse.setDesError("defaultDesError");
            return dataResponse;
        }

        public static LoginMWCredentialDataResponse.Role roleWithDefault() {
            LoginMWCredentialDataResponse.Role role = new LoginMWCredentialDataResponse.Role();
            role.setRolePersonId(1);
            role.setDescription("defaultDescription");
            role.setPersonId(1);
            role.setRelatedPersonId(2);
            role.setCompanyName("defaultCompanyName");
            role.setName("defaultName");
            role.setRoleCode(1);
            role.setUpdateUse(0);
            return role;
        }

}