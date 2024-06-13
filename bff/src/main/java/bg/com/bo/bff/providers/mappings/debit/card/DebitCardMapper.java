package bg.com.bo.bff.providers.mappings.debit.card;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsPeriod;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLockStatusRequest;
import bg.com.bo.bff.application.dtos.response.debit.card.AccountTD;
import bg.com.bo.bff.application.dtos.response.debit.card.DebitCard;
import bg.com.bo.bff.application.dtos.response.debitcard.DCInternetAuthorization;
import bg.com.bo.bff.application.dtos.response.debitcard.InternetAuthorizationResponse;
import bg.com.bo.bff.commons.enums.debit.card.StatusType;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLockStatusMWRequest;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponse;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLimitsMWRequest;
import bg.com.bo.bff.providers.dtos.response.debit.card.AccountsDebitCardMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.ListDebitCardMWResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCDetailMWResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import java.util.Objects;

@Component
public class DebitCardMapper implements IDebitCardMapper {
    @Override
    public DCLimitsMWRequest mapToLimitsRequest(DCLimitsRequest request, String personId, String cardId) {
        return DCLimitsMWRequest.builder()
                .numberOperations(request.getDailyCount())
                .personId(personId)
                .expirationDate(request.getPeriod().getEnd())
                .pciId(cardId)
                .amount(request.getDailyAmount())
                .build();
    }

    @Override
    public DCLockStatusMWRequest mapToLockStatusRequest(String personId, String cardId, DCLockStatusRequest request) {
        return DCLockStatusMWRequest.builder()
                .pciId(cardId)
                .personId(personId)
                .lockStatus(String.valueOf(request.getType()))
                .comment(StatusType.getDescriptionByCode(String.valueOf(request.getType())))
                .build();
    }

    @Override
    public List<DebitCard> convertResponseListDebitCard(ListDebitCardMWResponse listDebitCardMWResponse) {
        return Optional.ofNullable(listDebitCardMWResponse)
                .map(ListDebitCardMWResponse::getData)
                .orElseGet(List::of)
                .stream()
                .map(this::convertDebitCardMWToDebitCard)
                .toList();
    }

    private DebitCard convertDebitCardMWToDebitCard(ListDebitCardMWResponse.DebitCardMW debitCardMW) {
        return DebitCard.builder()
                .id(debitCardMW.getIdPci())
                .cardNumber(debitCardMW.getCardId())
                .holderName(debitCardMW.getCardName())
                .expiryDate(debitCardMW.getExpirationDate())
                .status(debitCardMW.getStatusDescription())
                .build();
    }

    @Override
    public List<AccountTD> convertResponseAccountListTD(AccountsDebitCardMWResponse mwResponse) {
        return mwResponse.getData().stream()
                .map(accountDebitCard -> AccountTD.builder()
                        .id(String.valueOf(accountDebitCard.getJtsOid()))
                        .accountNumber(String.valueOf(accountDebitCard.getAccountNumber()))
                        .description(accountDebitCard.getProductType())
                        .build())
                .toList();
    }

    @Override
    public InternetAuthorizationResponse mapToInternetAuthorizationResponse(DCInternetAuthorizationNWResponse response) {

        List<DCInternetAuthorization> result = response.getData().stream().map(x -> DCInternetAuthorization.builder()
                .id(x.getInternetIdTjTD())
                .amount(x.getAmount())
                .period(new DCLimitsPeriod(x.getStartDate(), x.getEndDate()))
                .build()
        ).toList();
        return InternetAuthorizationResponse.builder()
                .data(result)
                .build();
    }

    @Override
    public DCDetailResponse mapToDetailResponse(DCDetailMWResponse response) {
        return DCDetailResponse.builder()
                .cardNumber(response.getData().getCardNumber())
                .holderName(response.getData().getCardName())
                .expirationDate(response.getData().getExpirationDate())
                .status(response.getData().getStatus())
                .assured(Objects.equals(response.getData().getProtectionInsurance(), "S"))
                .build();
    }
}
