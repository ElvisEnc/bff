package bg.com.bo.bff.providers.dtos.request.keycloak;

import bg.com.bo.bff.commons.enums.user.UserRole;
import lombok.Singular;

import java.util.List;

@lombok.Data
@lombok.Builder
public class CustomClaimsData {

    @Singular("personId")
    private List<String> personId;
    @Singular("sid")
    private List<String> sid;
    @Singular("role")
    private List<UserRole> roles;
}
