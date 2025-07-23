package bg.com.bo.bff.providers.dtos.request.credit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockCreditCardMWRequest {
    private String cmsCardNumber;
    private String personId;
    private String codBlockType;
    private String codBlockReason;
    private String typeCard;
}
