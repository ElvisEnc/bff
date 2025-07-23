package bg.com.bo.bff.providers.dtos.request.remittance.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWURemittanceMWRequest {

    private String personId;
    private String applicationId;
    private String noConsult;
    private String relation;
    private String origin;
    private String transaction;
    private String company;
    private String companyLevel;
    private String entryDate;
    private String laborType;

}