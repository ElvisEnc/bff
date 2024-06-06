package bg.com.bo.bff.providers.mappings.debit.card;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLimitsMWRequest;

public interface IDebitCardMapper {
    DCLimitsMWRequest mapToLimitsRequest(DCLimitsRequest request, String personId, String cardId);
}
