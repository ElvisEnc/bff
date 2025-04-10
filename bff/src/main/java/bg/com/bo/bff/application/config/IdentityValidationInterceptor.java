package bg.com.bo.bff.application.config;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.models.UserData;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.TreeMap;

@Component
public class IdentityValidationInterceptor implements HandlerInterceptor {
    private final String identityValidationName = "personId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        /*Object variablesAttributes = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (variablesAttributes == null)
            return true;

        Map<String, String> map = new TreeMap<>((Map<String, String>) variablesAttributes);
        String personId = map.get(identityValidationName);

        if (personId == null)
            return true;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserData currentUserName = (UserData) authentication.getPrincipal();
            if (!currentUserName.getPersonId().equals(personId))
                throw new GenericException(DefaultMiddlewareError.NOT_AUTHENTICATED_VALID_USER);
        } else
            throw new GenericException(DefaultMiddlewareError.NOT_AUTHENTICATED_USER);
*/
        return true;
    }
}
