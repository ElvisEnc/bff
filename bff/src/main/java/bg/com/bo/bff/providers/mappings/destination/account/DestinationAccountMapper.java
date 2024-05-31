package bg.com.bo.bff.providers.mappings.destination.account;

import bg.com.bo.bff.application.dtos.response.BranchOfficeDataResponse;
import bg.com.bo.bff.application.dtos.response.BranchOfficeResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.commons.enums.DestinationAccountType;
import bg.com.bo.bff.models.ThirdAccount;
import bg.com.bo.bff.providers.dtos.response.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.response.account.ach.AchAccountMW;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DestinationAccountMapper implements IDestinationAccountMapper {
    public BranchOfficeResponse mapToBranchOfficeResponse(BranchOfficeMWResponse mwResponse) {
        List<BranchOfficeDataResponse> dataList = new ArrayList<>();
        if (mwResponse != null && mwResponse.getData() != null) {
            for (BranchOfficeMWResponse.BranchOfficeMWData.BranchOfficeArray branchOfficeArray : mwResponse.getData().getResponse()) {
                BranchOfficeDataResponse data = BranchOfficeDataResponse.builder()
                        .id(branchOfficeArray.getBranchCode())
                        .description(branchOfficeArray.getDescription())
                        .build();
                dataList.add(data);
            }
        }
        return BranchOfficeResponse.builder()
                .data(dataList)
                .build();
    }

    public DestinationAccount convertThirdAccountToDestinationAccount(ThirdAccount account, Integer type, String name) {
        return DestinationAccount.builder()
                .id(Long.valueOf(account.getId()))
                .accountId(Long.valueOf(account.getAccountId()))
                .accountNumber(Long.valueOf(account.getAccountNumber().trim()))
                .currencyCode(account.getCurrencyCode())
                .currencyAcronym(account.getCurrencyAcronym())
                .clientName(account.getClientName())
//                .bankCode("") // null
                .bankName(name)
                .accountAliases(account.getAccountAliases())
                .destinationAccountType(type)
                .build();
    }

    public DestinationAccount convertAchAccountToDestinationAccount(AchAccountMW achAccount) {
        return DestinationAccount.builder()
                .id(Long.valueOf(achAccount.getIdList()))
//                .accountId()  // null
                .accountNumber(Long.valueOf(achAccount.getAccountNumber()))
//                .currencyCode("") // null
//                .currencyAcronym("") // null
                .clientName(achAccount.getHolderName())
                .bankCode(achAccount.getBankCode())
                .bankName(achAccount.getBankName())
                .accountAliases(achAccount.getAccountNickname())
                .destinationAccountType(DestinationAccountType.CUENTA_ACH.getCode())
                .build();
    }
}
