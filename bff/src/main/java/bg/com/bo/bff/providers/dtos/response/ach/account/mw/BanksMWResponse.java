package bg.com.bo.bff.providers.dtos.response.ach.account.mw;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BanksMWResponse {
    private List<BankMW> data;
}


