package bg.com.bo.bff.providers.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrListMWRequest {
    private String personId;
    private String startDate;
    private String endDate;
}
