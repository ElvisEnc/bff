package bg.com.bo.bff.providers.mappings.transfer;

import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.models.ThirdAccountListResponse;
import bg.com.bo.bff.providers.dtos.requests.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.requests.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.responses.ThirdAccountListMWResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransferMWtMapper {
    TransferMWtMapper INSTANCE = Mappers.getMapper(TransferMWtMapper.class);

    @Mappings({
            @Mapping(target = "ownerAccount.schemeName", constant = "personId"),
            @Mapping(target = "ownerAccount.personId", source = "personId"),
            @Mapping(target = "debtorAccount.schemeName", constant = "accountId"),
            @Mapping(target = "debtorAccount.identification", source = "accountId"),
            @Mapping(target = "creditorAccount.schemeName", constant = "accountId"),
            @Mapping(target = "creditorAccount.identification", source = "transferRequest.targetAccount.id"),
            @Mapping(target = "instructedAmount.currency", source = "transferRequest.amount.currency"),
            @Mapping(target = "instructedAmount.amount", source = "transferRequest.amount.amount"),
            @Mapping(target = "supplementaryData.description", source = "transferRequest.data.description"),
            @Mapping(target = "supplementaryData.sourceOfFunds", source = "transferRequest.data.sourceOfFounds"),
            @Mapping(target = "supplementaryData.destinationOfFunds", source = "transferRequest.data.destinationOfFounds")
    })
    TransferMWRequest convert(String personId, String accountId, TransferRequest transferRequest);
}


