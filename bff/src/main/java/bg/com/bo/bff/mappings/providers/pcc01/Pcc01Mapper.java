package bg.com.bo.bff.mappings.providers.pcc01;

import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.providers.dtos.response.Pcc01MWResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface Pcc01Mapper {
    Pcc01Mapper INSTANCE= Mappers.getMapper(Pcc01Mapper.class);
    Pcc01Response convert(Pcc01MWResponse response);
}
