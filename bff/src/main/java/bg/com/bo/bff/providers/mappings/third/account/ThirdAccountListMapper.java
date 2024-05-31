package bg.com.bo.bff.providers.mappings.third.account;

import bg.com.bo.bff.providers.dtos.response.ThirdAccountListMWResponse;
import bg.com.bo.bff.models.ThirdAccountListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ThirdAccountListMapper {
    ThirdAccountListMapper INSTANCE = Mappers.getMapper(ThirdAccountListMapper.class);
    ThirdAccountListResponse convert(ThirdAccountListMWResponse accountListMWResponse);
}
