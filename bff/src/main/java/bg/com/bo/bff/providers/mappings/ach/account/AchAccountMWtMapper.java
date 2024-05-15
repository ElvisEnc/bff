package bg.com.bo.bff.providers.mappings.ach.account;

import bg.com.bo.bff.providers.dtos.requests.DeleteAchAccountMWRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AchAccountMWtMapper {
    AchAccountMWtMapper INSTANCE = Mappers.getMapper(AchAccountMWtMapper.class);

    DeleteAchAccountMWRequest convert(String personId, long identifier);
}
