package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountListMWResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.ThirdAccountListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ThirdAccountListMapper {
    ThirdAccountListMapper INSTANCE = Mappers.getMapper(ThirdAccountListMapper.class);
    ThirdAccountListResponse convert(ThirdAccountListMWResponse accountListMWResponse);
}
