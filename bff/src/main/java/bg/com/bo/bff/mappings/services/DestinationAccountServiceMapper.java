package bg.com.bo.bff.mappings.services;

import bg.com.bo.bff.application.dtos.response.destination.account.AccountType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DestinationAccountServiceMapper {
    DestinationAccountServiceMapper INSTANCE = Mappers.getMapper(DestinationAccountServiceMapper.class);

    List<AccountType> convert(List<bg.com.bo.bff.commons.enums.destination.account.AccountType> list);

    AccountType convert(bg.com.bo.bff.commons.enums.destination.account.AccountType accountType);
}
