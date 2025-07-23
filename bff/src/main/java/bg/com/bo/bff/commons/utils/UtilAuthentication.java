package bg.com.bo.bff.commons.utils;

import bg.com.bo.bff.models.UserData;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

public class UtilAuthentication {
    public static UserData getUserData(Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken))
            return (UserData) authentication.getPrincipal();
        else
            return null;
    }
}
