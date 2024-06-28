package bg.com.bo.bff.mappings.providers.card;

import bg.com.bo.bff.application.dtos.request.debit.card.*;
import bg.com.bo.bff.application.dtos.response.debit.card.AccountTD;
import bg.com.bo.bff.application.dtos.response.debit.card.DebitCard;
import bg.com.bo.bff.application.dtos.response.debitcard.DCInternetAuthorization;
import bg.com.bo.bff.application.dtos.response.debitcard.InternetAuthorizationResponse;
import bg.com.bo.bff.commons.enums.debit.card.StatusType;
import bg.com.bo.bff.providers.dtos.request.debit.card.*;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponse;
import bg.com.bo.bff.application.dtos.response.debit.card.DCDetailResponse;
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
    public DCAccountsOrderMWRequest mapToAccountsOrderRequest(String personId, String cardId, DCAccountsOrderRequest request) {
        List<DCAccountsOrderRequest.Account> accounts = request.getData();
        String[] accountIds = new String[4];

        for (int i = 0; i < accountIds.length; i++) {
            if (i < accounts.size()) {
                accountIds[i] = String.valueOf(accounts.get(i).getAccountId());
            } else {
                accountIds[i] = "0";
            }
        }

        return DCAccountsOrderMWRequest.builder()
                .accountId1(accountIds[0])
                .accountId2(accountIds[1])
                .accountId3(accountIds[2])
                .accountId4(accountIds[3])
                .pciId(cardId)
                .personId(personId)
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
                        .accountId(String.valueOf(accountDebitCard.getJtsOid()))
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
    public DeleteAuthPurchaseMWRequest mapDeleteAuthRequest(Integer personId, Integer cardId, Integer authId) {
        return DeleteAuthPurchaseMWRequest.builder()
                .identifierTD(String.valueOf(authId))
                .personId(String.valueOf(personId))
                .action("B")
                .idPci(String.valueOf(cardId))
                .build();
    }

    @Override
    public UpdateDebitCardSecureMWRequest mapActiveAssuranceRequest(Integer personId, Integer cardId, UpdateDebitCardAssuranceRequest request) {
        return UpdateDebitCardSecureMWRequest.builder()
                .personId(String.valueOf(personId))
                .debitCardNew(request.getOpeningRequestFlow() ? "S" : "N")
                .pciId(String.valueOf(cardId))
                .acceptInsurance("S")
                .email(request.getEmail())
                .requestNumberOld(request.getOpeningRequestNumber())
                .build();
    }

    @Override
    public ActivateDebitCardMWRequest mapActivateDebitCardRequest(Integer personId, Integer cardId) {
        return ActivateDebitCardMWRequest.builder()
                .idPci(String.valueOf(cardId))
                .personId(String.valueOf(personId))
                .build();
    }

    @Override
    public CreateAuthorizationOnlinePurchaseMWRequest mapToCreateAuthorizationOnlinePurchaseMWRequest(CreateAuthorizationOnlinePurchaseRequest request,
                                                                                                      String cardId,
                                                                                                      Integer intInitial,
                                                                                                      Integer intFinal,
                                                                                                      String action) {
        return CreateAuthorizationOnlinePurchaseMWRequest.builder()
                .idPci(cardId)
                .action(action)
                .initialDate(request.getPeriod().getStart())
                .finalDate(request.getPeriod().getEnd())
                .amount(request.getAmount())
                .intInitial(intInitial)
                .intFinal(intFinal)
                .build();

    }

    @Override
    public DCDetailResponse mapToDetailResponse(DCDetailMWResponse response) {
        return DCDetailResponse.builder()
                .cardNumber(response.getData().getCardNumber())
                .holderName(response.getData().getCardName())
                .expirationDate(response.getData().getExpirationDate())
                .status(response.getData().getStatus())
                .statusDescription(response.getData().getStatusDescription())
                .assured(Objects.equals(response.getData().getProtectionInsurance(), "S"))
                .limitExpirationDate(response.getData().getLimitExpirationDate())
                .limitAmount(response.getData().getLimitAmountME())
                .limitNumber(response.getData().getLimitExtractions())
                .build();
    }
}
