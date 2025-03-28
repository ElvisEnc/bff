package bg.com.bo.bff.application.dtos.response.remittance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWURemittanceResponse {

    private String codeError;
    private String company;
    private String companyLevel;
    private String entryDate;
    private String laborType;
    private String pcc01;

}
