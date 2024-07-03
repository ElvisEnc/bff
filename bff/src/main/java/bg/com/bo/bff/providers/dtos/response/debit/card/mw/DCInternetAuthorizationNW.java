package bg.com.bo.bff.providers.dtos.response.debit.card.mw;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DCInternetAuthorizationNW {
    private String status;
    private String type;
    private String startDate;
    private String endDate;
    private String amount;
    private String currency;
    private String cardNumber;
    private String internetIdTjTD;
    private String processDate;
}
