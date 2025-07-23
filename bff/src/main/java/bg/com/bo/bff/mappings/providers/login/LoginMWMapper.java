package bg.com.bo.bff.mappings.providers.login;

import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.ChangePasswordMWRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.LoginFactorMWRequest;
import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.MWOwnerAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoginMWMapper {
    LoginMWMapper INSTANCE = Mappers.getMapper(LoginMWMapper.class);

    @Mapping(source = "type", target = "codeTypeAuthentication")
    @Mapping(source = "user", target = "factor")
    @Mapping(target = "deviceIdentification.deviceIp", ignore = true)
    LoginFactorMWRequest convert(LoginRequest loginRequest);

    @Mapping(target = "newPassword", expression = "java(bg.com.bo.bff.commons.utils.Util.encodeSha512(input.getNewPassword()))")
    @Mapping(target = "previousPassword", expression = "java(bg.com.bo.bff.commons.utils.Util.encodeSha512(input.getOldPassword()))")
    @Mapping(target = "ownerAccount", source = "ownerAccountRequest")
    ChangePasswordMWRequest convert(ChangePasswordRequest input, MWOwnerAccountRequest ownerAccountRequest);

    @Mapping(target = "personRoleId", source = "rolePersonId")
    MWOwnerAccountRequest convert(String personId, String userDeviceId, String rolePersonId);
}
