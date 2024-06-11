package bg.com.bo.bff.application.dtos.response.debit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListDebitCardResponse {
    private List<DebitCard> data;
}
