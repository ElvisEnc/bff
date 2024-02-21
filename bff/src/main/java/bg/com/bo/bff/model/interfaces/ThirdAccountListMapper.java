package bg.com.bo.bff.model.interfaces;

import bg.com.bo.bff.model.ThirdAccountListMWResponse;
import bg.com.bo.bff.model.ThirdAccountListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ThirdAccountListMapper {
    ThirdAccountListMapper INSTANCE = Mappers.getMapper(ThirdAccountListMapper.class);
    ThirdAccountListResponse convert(ThirdAccountListMWResponse accountListMWResponse);
}
