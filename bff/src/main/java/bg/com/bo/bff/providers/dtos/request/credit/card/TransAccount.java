package bg.com.bo.bff.providers.dtos.request.credit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransAccount {
    private String schemeName;
    private String identification;
}
