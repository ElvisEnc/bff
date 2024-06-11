package bg.com.bo.bff.application.dtos.response.debitcard;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsPeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class InternetAuthorizationResponse {

    private List<DCInternetAuthorization> data;

}

