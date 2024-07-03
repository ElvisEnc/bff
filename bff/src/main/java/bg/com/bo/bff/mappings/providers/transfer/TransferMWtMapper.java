package bg.com.bo.bff.mappings.providers.transfer;

import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequest;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransferMWtMapper {
    TransferMWtMapper INSTANCE = Mappers.getMapper(TransferMWtMapper.class);

    @Mappings({
            @Mapping(target = "ownerAccount.schemeName", expression = "java(bg.com.bo.bff.commons.utils.Util.getTransferSchemeName(typeTransfer, \"owner\"))"),
            @Mapping(target = "ownerAccount.personId", source = "personId"),
            @Mapping(target = "debtorAccount.schemeName", expression = "java(bg.com.bo.bff.commons.utils.Util.getTransferSchemeName(typeTransfer, \"debtor\"))"),
            @Mapping(target = "debtorAccount.identification", source = "accountId"),
            @Mapping(target = "creditorAccount.schemeName", expression = "java(bg.com.bo.bff.commons.utils.Util.getTransferSchemeName(typeTransfer, \"creditor\"))"),
            @Mapping(target = "creditorAccount.identification", source = "transferRequest.targetAccount.id"),
            @Mapping(target = "instructedAmount.currency", source = "transferRequest.amount.currency"),
            @Mapping(target = "instructedAmount.amount", source = "transferRequest.amount.amount"),
            @Mapping(target = "supplementaryData.description", source = "transferRequest.data.description"),
            @Mapping(target = "supplementaryData.sourceOfFunds", source = "transferRequest.data.sourceOfFounds"),
            @Mapping(target = "supplementaryData.destinationOfFunds", source = "transferRequest.data.destinationOfFounds")
    })
    TransferMWRequest convert(@Context String typeTransfer, String personId, String accountId, TransferRequest transferRequest);
}


