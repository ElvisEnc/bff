package bg.com.bo.bff.mappings.services;

import bg.com.bo.bff.application.dtos.response.AccountTypeListResponse;
import bg.com.bo.bff.application.dtos.response.AccountTypeResponse;
import bg.com.bo.bff.application.dtos.response.LoginResponse;
import bg.com.bo.bff.commons.enums.AccountType;
import bg.com.bo.bff.models.dtos.login.LoginResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DestinationAccountServiceMapper {
    DestinationAccountServiceMapper INSTANCE = Mappers.getMapper(DestinationAccountServiceMapper.class);

    List<AccountTypeResponse> convert(List<AccountType> list);

    AccountTypeResponse convert(AccountType accountType);
}
