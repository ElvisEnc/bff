package bg.com.bo.bff.providers.dtos.response.ach.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchOfficeMWResponse {
    private List<BranchOfficeMW> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BranchOfficeMW {
        private String description;
        private String branchCode;
        private String entityCode;
    }
}
