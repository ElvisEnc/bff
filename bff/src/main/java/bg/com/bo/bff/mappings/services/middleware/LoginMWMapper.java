package bg.com.bo.bff.mappings.services.middleware;

import bg.com.bo.bff.provider.request.login.LoginMWRequest;
import bg.com.bo.bff.controllers.request.LoginRequest;
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
