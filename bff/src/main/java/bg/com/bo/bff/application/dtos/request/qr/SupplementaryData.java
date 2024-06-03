package bg.com.bo.bff.application.dtos.request.qr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplementaryData {
    private String idQr;
    private String nroDni;
    private String description;
    private String dueDate;
    private String typeUse;
    private String serviceCode;
    private String fields;
    private String serialNumber;
}
