package bg.com.bo.bff.providers.mappings.third.account;

import bg.com.bo.bff.models.ThirdAccountListResponse;
import bg.com.bo.bff.providers.dtos.requests.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.responses.ThirdAccountListMWResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ThirdAccountMWtMapper {
    ThirdAccountMWtMapper INSTANCE = Mappers.getMapper(ThirdAccountMWtMapper.class);

    ThirdAccountListResponse convert(ThirdAccountListMWResponse accountListMWResponse);

    @Mapping(target = "accountNumber", source = "accountId")
    DeleteThirdAccountMWRequest convert(String personId, int identifier, int accountId);
}
