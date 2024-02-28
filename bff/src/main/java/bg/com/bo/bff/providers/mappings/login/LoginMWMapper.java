package bg.com.bo.bff.providers.mappings.login;

import bg.com.bo.bff.providers.dtos.requests.login.LoginMWRequest;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoginMWMapper {
    LoginMWMapper INSTANCE = Mappers.getMapper(LoginMWMapper.class);

    @Mapping(source = "complemento", target = "dniComplement")
    @Mapping(source = "type", target = "schemeName")
    @Mapping(source = "personId", target = "user")
    @Mapping(target = "deviceIp", ignore = true)
    LoginMWRequest convert(LoginRequest loginRequest);
}
