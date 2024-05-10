package bg.com.bo.bff.application.dtos.response.qr;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrListResponse {
    private List<QrGeneratedPaid> generated;
    private Integer totalGenerated;
    private List<QrGeneratedPaid> paid;
    private Integer totalPaid;
}
