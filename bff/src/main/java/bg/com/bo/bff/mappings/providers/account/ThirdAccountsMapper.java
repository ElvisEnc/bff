package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.request.destination.account.AddQRAccountRequest;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountsMWResponse;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Component
public class ThirdAccountsMapper implements IThirdAccountsMapper {
    @Override
    public DeleteThirdAccountMWRequest mapperRequest(String personId, long identifier, long accountNumber) {
        return DeleteThirdAccountMWRequest.builder()
                .personId(personId)
                .identifier(String.valueOf(identifier))
                .accountNumber(String.valueOf(accountNumber))
                .build();
    }

    @Override
    public List<DestinationAccount> convertThirdAccountToDestinationAccount(ThirdAccountsMWResponse mwResponse, Integer type, String name) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> DestinationAccount.builder()
                        .id(Long.valueOf(mw.getIdentifier()))
                        .accountId(Long.valueOf(mw.getAccountId()))
                        .accountNumber(new BigInteger(mw.getAccountNumber().trim()))
                        .currencyCode(mw.getCurrencyCode())
                        .currencyAcronym(mw.getCurrencyAcronym())
                        .clientName(mw.getClientName())
                        //.bankCode("") // null
                        .bankName(name)
                        .accountAliases(mw.getAccountAliases())
                        .destinationAccountType(type)
                        .build())
                .toList();
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
}
