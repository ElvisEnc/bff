package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountListMWResponse;
import bg.com.bo.bff.application.dtos.response.own.account.AccountListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountListMapper {
    AccountListMapper INSTANCE = Mappers.getMapper(AccountListMapper.class);

    AccountListResponse convert(AccountListMWResponse accountListMWResponse);
}
