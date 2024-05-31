package bg.com.bo.bff.providers.mappings.own.account;

import bg.com.bo.bff.providers.dtos.response.accounts.AccountListMWResponse;
import bg.com.bo.bff.models.dtos.accounts.AccountListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountListMapper {
    AccountListMapper INSTANCE = Mappers.getMapper(AccountListMapper.class);

    AccountListResponse convert(AccountListMWResponse accountListMWResponse);
}
