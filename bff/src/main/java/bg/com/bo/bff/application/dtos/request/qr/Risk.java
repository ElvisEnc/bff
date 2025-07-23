package bg.com.bo.bff.application.dtos.request.qr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Risk {
    private String paymentContextCode;
}
