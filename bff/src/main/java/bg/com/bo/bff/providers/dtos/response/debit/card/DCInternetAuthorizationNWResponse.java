package bg.com.bo.bff.providers.dtos.response.debit.card;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DCInternetAuthorizationNWResponse {

    private List<DCInternetAuthorizationNW> data;
}


