package bg.com.bo.bff.providers.mappings.login;

import bg.com.bo.bff.providers.dtos.requests.login.LoginMWFactorRequest;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoginMWMapper {
    LoginMWMapper INSTANCE = Mappers.getMapper(LoginMWMapper.class);

    @Mapping(source = "type", target = "codeTypeAuthentication")
    @Mapping(source = "user", target = "factor")
    @Mapping(target = "deviceIdentification.deviceIp", ignore = true)
    LoginMWFactorRequest convert(LoginRequest loginRequest);
}
