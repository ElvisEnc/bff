package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.request.destination.account.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.AddQRAccountRequest;
import bg.com.bo.bff.application.dtos.response.destination.account.BranchOfficeDataResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.BranchOfficeResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.commons.enums.destination.account.DestinationAccountType;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.DeleteAchAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AchAccountsMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BranchOfficeMWResponse;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AchAccountsMapper implements IAchAccountsMapper {
    @Override
    public AddAchAccountBasicRequest mapperRequest(String personId, AddAchAccountRequest addAchAccountRequest) {
        return AddAchAccountBasicRequest.builder()
                .personId(personId)
                .companyPersonId(personId)
                .isFavorite(addAchAccountRequest.getIsFavorite())
                .isEnabled(addAchAccountRequest.getIsEnabled())
                .reference(addAchAccountRequest.getReference())
                .destinationAccountNumber(addAchAccountRequest.getDestinationAccountNumber())
                .destinationBankCode(addAchAccountRequest.getDestinationBankCode())
                .destinationBranchOfficeCode(addAchAccountRequest.getDestinationBranchOfficeCode())
                .destinationAccountTypeCode(addAchAccountRequest.getDestinationAccountTypeCode())
                .destinationHolderName(addAchAccountRequest.getDestinationHolderName())
                .destinationIDNumber(addAchAccountRequest.getDestinationIDNumber())
                .email(addAchAccountRequest.getEmail())
                .build();
    }

    @Override
    public DeleteAchAccountMWRequest mapperRequest(String personId, long identifier) {
        return DeleteAchAccountMWRequest.builder()
                .personId(personId)
                .identifier(String.valueOf(identifier))
                .build();
    }

    @Override
    public BranchOfficeResponse mapToBranchOfficeResponse(BranchOfficeMWResponse mwResponse) {
        List<BranchOfficeDataResponse> dataList = new ArrayList<>();
        if (mwResponse != null && mwResponse.getData() != null) {
            for (BranchOfficeMWResponse.BranchOfficeMW branchOfficeArray : mwResponse.getData()) {
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

    @Override
    public List<DestinationAccount> convertAchAccountToDestinationAccount(AchAccountsMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> DestinationAccount.builder()
                        .id(Long.valueOf(mw.getIdList()))
//                        .accountId()  // null
                        .accountNumber(new BigInteger(mw.getAccountNumber()))
//                        .currencyCode("") // null
//                        .currencyAcronym("") // null
                        .clientName(mw.getHolderName())
                        .bankCode(mw.getBankCode())
                        .bankName(mw.getBankName())
                        .accountAliases(mw.getAccountNickname())
                        .destinationAccountType(DestinationAccountType.CUENTA_ACH.getCode())
                        .build())
                .toList();
    }

    @Override
    public AddAchAccountBasicRequest mapToAchRequest(String personId, AddQRAccountRequest request) {
        return AddAchAccountBasicRequest.builder()
                .personId(personId)
                .companyPersonId(personId)
                .isFavorite("N")
                .isEnabled("S")
                .reference(request.getReference())
                .destinationAccountNumber(request.getAccountNumber())
                .destinationBankCode(request.getBankCode())
                .destinationBranchOfficeCode("SCZ")
                .destinationAccountTypeCode(request.getAccountNumber().length() == 8 ? "CMOVILD" : "CCAD")
                .destinationHolderName(request.getHolderName())
                .destinationIDNumber(request.getIdentificationNumber())
                .build();
    }
}
