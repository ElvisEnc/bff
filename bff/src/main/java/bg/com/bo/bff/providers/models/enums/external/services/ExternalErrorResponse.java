package bg.com.bo.bff.providers.models.enums.external.services;

import lombok.Data;

import java.util.List;

@Data
public class ExternalErrorResponse {
    private List<ErrorDetail> errorDetailResponse;

    public ExternalErrorResponse(List<ErrorDetail> errorDetailResponse) {
        this.errorDetailResponse = errorDetailResponse;
    }

    @Data
    public static class ErrorDetail {
        private String code;
        private String description;

        public ErrorDetail(String code, String description) {
            this.code = code;
            this.description = description;
        }

    }
}

