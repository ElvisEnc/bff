package bg.com.bo.bff.providers.dtos.responses;

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
    private BranchOfficeMWData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BranchOfficeMWData {
        private List<BranchOfficeArray> response;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class BranchOfficeArray {
            private String description;
            private String branchCode;
            private String entityCode;
        }
    }
}
