package bg.com.bo.bff.model.jwt;

import bg.com.bo.bff.model.enums.UserRole;
import lombok.Singular;

import java.util.List;

@lombok.Data
@lombok.Builder
public class CustomClaimsData {

    @Singular("personId")
    private List<String> personId;
    @Singular("role")
    private List<UserRole> roles;
}
