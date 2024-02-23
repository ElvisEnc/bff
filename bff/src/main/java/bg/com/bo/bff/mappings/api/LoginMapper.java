package bg.com.bo.bff.mappings.api;

import bg.com.bo.bff.controllers.response.LoginResponse;
import bg.com.bo.bff.model.dtos.login.LoginResult;
import bg.com.bo.bff.model.dtos.login.RefreshSessionResult;
import bg.com.bo.bff.controllers.response.TokenDataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    LoginMapper INSTANCE = Mappers.getMapper(LoginMapper.class);

    @Mapping(source = "refreshToken", target = "refreshToken")
    @Mapping(source = "accessToken", target = "accessToken")
    TokenDataResponse convert(RefreshSessionResult refreshSessionResult);

    @Mapping(source = "tokenData.refreshToken", target = "refreshToken")
    @Mapping(source = "tokenData.accessToken", target = "accessToken")
    LoginResponse convert(LoginResult loginRequest);
}
