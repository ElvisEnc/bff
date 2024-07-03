package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.providers.dtos.request.ach.account.mw.DeleteAchAccountMWRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AchAccountMWtMapper {
    AchAccountMWtMapper INSTANCE = Mappers.getMapper(AchAccountMWtMapper.class);

    DeleteAchAccountMWRequest convert(String personId, long identifier);
}
