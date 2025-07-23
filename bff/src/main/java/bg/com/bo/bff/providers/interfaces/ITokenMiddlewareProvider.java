package bg.com.bo.bff.providers.interfaces;

import java.io.IOException;

import bg.com.bo.bff.models.ClientToken;

public interface ITokenMiddlewareProvider {
    ClientToken generateAccountAccessToken(String project, String clientSecret, String headerKeyToken) throws IOException;
}
