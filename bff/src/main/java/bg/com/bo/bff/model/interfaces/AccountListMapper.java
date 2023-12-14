package bg.com.bo.bff.model.interfaces;

import bg.com.bo.bff.model.AccountListMWResponse;
import bg.com.bo.bff.model.AccountListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountListMapper {
    AccountListMapper INSTANCE = Mappers.getMapper(AccountListMapper.class);

    AccountListResponse convert(AccountListMWResponse accountListMWResponse);
}
