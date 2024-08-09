package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.request.destination.account.AddQRAccountRequest;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddThirdAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.AddWalletAccountBasicRequest;
import bg.com.bo.bff.providers.dtos.request.third.account.mw.DeleteThirdAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.third.account.mw.ThirdAccountsMWResponse;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class ThirdAccountsMapper implements IThirdAccountsMapper {
    @Override
    public DeleteThirdAccountMWRequest mapperRequest(String personId, long identifier, long accountNumber) {
        return  DeleteThirdAccountMWRequest.builder()
                .personId(personId)
                .identifier(String.valueOf(identifier))
                .accountNumber(String.valueOf(accountNumber))
                .build();
    }

    @Override
    public DestinationAccount convertThirdAccountToDestinationAccount(ThirdAccountsMWResponse.ThirdAccountMW account, Integer type, String name) {
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
