package bg.com.bo.bff.application.dtos.response.destination.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationAccountResponse {
    private List<DestinationAccount> data;
    private Integer total;
}
