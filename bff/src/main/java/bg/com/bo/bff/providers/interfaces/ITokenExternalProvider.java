package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.token.external.TokenAuthenticationRequestDto;

import java.io.IOException;

public interface ITokenExternalProvider {
    ClientToken generateAccountAccessToken(TokenAuthenticationRequestDto tokenAuthenticationRequestDto) throws IOException;
}
