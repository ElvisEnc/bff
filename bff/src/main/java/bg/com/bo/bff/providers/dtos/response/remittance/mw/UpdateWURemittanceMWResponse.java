package bg.com.bo.bff.providers.dtos.response.remittance.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWURemittanceMWResponse {

    private UpdateWURemittanceMWResponse.UpdateWURemittanceMW data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateWURemittanceMW {

        private String codeError;
        private String company;
        private String companyLevel;
        private String entryDate;
        private String laborType;
        private String pcc01;

    }
}
