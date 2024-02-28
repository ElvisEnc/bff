package bg.com.bo.bff.mappings.services;

import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.models.dtos.TokenDataServiceResponse;
import bg.com.bo.bff.models.dtos.login.CreateTokenServiceResponse;
import bg.com.bo.bff.models.dtos.login.LoginResult;
import bg.com.bo.bff.models.dtos.login.RefreshSessionResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoginServiceMapper {
    LoginServiceMapper INSTANCE = Mappers.getMapper(LoginServiceMapper.class);

    @Mapping(source = "tokenData.accessToken", target = "accessToken")
    @Mapping(source = "tokenData.refreshToken", target = "refreshToken")
    @Mapping(source = "statusCode", target = "statusCode")
    RefreshSessionResult convert(TokenDataServiceResponse tokenData, RefreshSessionResult.StatusCode statusCode);

    @Mapping(source = "createTokenResponse.tokenData.accessToken", target = "tokenData.accessToken")
    @Mapping(source = "createTokenResponse.tokenData.refreshToken", target = "tokenData.refreshToken")
    @Mapping(source = "statusCode", target = "statusCode")
    LoginResult convert(CreateTokenServiceResponse createTokenResponse, LoginValidationServiceResponse loginResult, LoginResult.StatusCode statusCode);
}
