package bg.com.bo.bff.application.mappings.login;

import bg.com.bo.bff.application.dtos.response.LoginResponse;
import bg.com.bo.bff.models.dtos.login.LoginResult;
import bg.com.bo.bff.models.dtos.login.RefreshSessionResult;
import bg.com.bo.bff.application.dtos.response.TokenDataResponse;
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
    @Mapping(source = "personId", target = "userData.personId")
    @Mapping(source = "userDeviceId", target = "userData.userDeviceId")
    @Mapping(source = "rolePersonId", target = "userData.rolePersonId")
    LoginResponse convert(LoginResult loginRequest);
}
