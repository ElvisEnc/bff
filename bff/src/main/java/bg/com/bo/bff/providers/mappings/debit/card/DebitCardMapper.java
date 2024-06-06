package bg.com.bo.bff.providers.mappings.debit.card;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLimitsMWRequest;
import org.springframework.stereotype.Component;

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
}
