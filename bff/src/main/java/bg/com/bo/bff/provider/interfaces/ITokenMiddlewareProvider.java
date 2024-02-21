package bg.com.bo.bff.provider.interfaces;

import java.io.IOException;

import bg.com.bo.bff.model.ClientToken;

public interface ITokenMiddlewareProvider {
    ClientToken generateAccountAccessToken(String project) throws IOException;

}
