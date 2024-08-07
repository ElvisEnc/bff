package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.request.destination.account.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.AddQRAccountRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.AddThirdAccountRequest;
import bg.com.bo.bff.application.dtos.response.destination.account.BranchOfficeDataResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.BranchOfficeResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.commons.enums.DestinationAccountType;
import bg.com.bo.bff.application.dtos.response.destination.account.ThirdAccount;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.AddAchAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.ach.account.mw.DeleteAchAccountMWRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BranchOfficeMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AchAccountMW;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class DestinationAccountMapper implements IDestinationAccountMapper {
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
        return  DeleteAchAccountMWRequest.builder()
                .personId(personId)
                .identifier(String.valueOf(identifier))
                .build();
    }

    @Override
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

    @Override
    public DestinationAccount convertThirdAccountToDestinationAccount(ThirdAccount account, Integer type, String name) {
        return DestinationAccount.builder()
                .id(Long.valueOf(account.getId()))
                .accountId(Long.valueOf(account.getAccountId()))
                .accountNumber(new BigInteger(account.getAccountNumber().trim()))
                .currencyCode(account.getCurrencyCode())
                .currencyAcronym(account.getCurrencyAcronym())
                .clientName(account.getClientName())
//                .bankCode("") // null
                .bankName(name)
                .accountAliases(account.getAccountAliases())
                .destinationAccountType(type)
                .build();
    }

    @Override
    public DestinationAccount convertAchAccountToDestinationAccount(AchAccountMW achAccount) {
        return DestinationAccount.builder()
                .id(Long.valueOf(achAccount.getIdList()))
//                .accountId()  // null
                .accountNumber(new BigInteger(achAccount.getAccountNumber()))
//                .currencyCode("") // null
//                .currencyAcronym("") // null
                .clientName(achAccount.getHolderName())
                .bankCode(achAccount.getBankCode())
                .bankName(achAccount.getBankName())
                .accountAliases(achAccount.getAccountNickname())
                .destinationAccountType(DestinationAccountType.CUENTA_ACH.getCode())
                .build();
    }

    @Override
    public AddThirdAccountBasicRequest mapToThirdRequest(String personId, AddQRAccountRequest request) {
        return AddThirdAccountBasicRequest.builder()
                .personId(personId)
                .companyPersonId(personId)
                .toAccountNumber(request.getAccountNumber())
                .reference(request.getReference())
                .isFavorite("N")
                .build();
    }

    @Override
    public AddWalletAccountBasicRequest mapToWalletRequest(String personId, AddQRAccountRequest request) {
        return AddWalletAccountBasicRequest.builder()
                .personId(personId)
                .companyPersonId(personId)
                .toAccountNumber(request.getAccountNumber())
                .reference(request.getReference())
                .isFavorite("N")
                .build();
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
