package bg.com.bo.bff.providers.mappings.debit.card;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsPeriod;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.application.dtos.response.debit.card.DebitCard;
import bg.com.bo.bff.application.dtos.response.debitcard.DCInternetAuthorization;
import bg.com.bo.bff.application.dtos.response.debitcard.InternetAuthorizationResponse;
import bg.com.bo.bff.providers.dtos.response.debit.card.DCInternetAuthorizationNWResponse;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLimitsMWRequest;
import bg.com.bo.bff.providers.dtos.response.debit.card.ListDebitCardMWResponse;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public DebitCard convertResponse(ListDebitCardMWResponse.DebitCardMW debitCardMW) {
        return DebitCard.builder()
                .id(debitCardMW.getIdPci())
                .cardNumber(debitCardMW.getCardId())
                .holderName(debitCardMW.getCardName())
                .expiryDate(debitCardMW.getExpirationDate())
                .status(debitCardMW.getStatusDescription())
                .build();
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
}
