package bg.com.bo.bff.application.dtos.request.qr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Amount {
    private String currency;
    private String amount;
}
