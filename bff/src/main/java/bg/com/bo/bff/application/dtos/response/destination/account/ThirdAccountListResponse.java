package bg.com.bo.bff.application.dtos.response.destination.account;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThirdAccountListResponse {
    private List<ThirdAccount> data;
}
