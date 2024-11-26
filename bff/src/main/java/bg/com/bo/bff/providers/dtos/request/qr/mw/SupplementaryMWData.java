package bg.com.bo.bff.providers.dtos.request.qr.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SupplementaryMWData {
    private String idQr;
    private String nroDni;
    private String description;
    private String dueDate;
    private String typeUse;
    private String serviceCode;
    private String fields;
    private String serialNumber;
    private String allowsDuplicate;
}
