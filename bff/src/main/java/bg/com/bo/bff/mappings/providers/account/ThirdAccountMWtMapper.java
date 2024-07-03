package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.response.destination.account.ThirdAccountListResponse;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountListMWResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ThirdAccountMWtMapper {
    ThirdAccountMWtMapper INSTANCE = Mappers.getMapper(ThirdAccountMWtMapper.class);

    ThirdAccountListResponse convert(ThirdAccountListMWResponse accountListMWResponse);

    @Mapping(target = "accountNumber", source = "accountId")
    DeleteThirdAccountMWRequest convert(String personId, long identifier, long accountId);
}
