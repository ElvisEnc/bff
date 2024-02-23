package bg.com.bo.bff.model.interfaces;

import bg.com.bo.bff.model.dtos.middleware.accounts.AccountListMWResponse;
import bg.com.bo.bff.model.dtos.accounts.AccountListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountListMapper {
    AccountListMapper INSTANCE = Mappers.getMapper(AccountListMapper.class);

    AccountListResponse convert(AccountListMWResponse accountListMWResponse);
}
