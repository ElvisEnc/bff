package bg.com.bo.bff.providers.dtos.request.qr.mw;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private String sourceOfFunds;
    private String destinationOfFunds;
}
