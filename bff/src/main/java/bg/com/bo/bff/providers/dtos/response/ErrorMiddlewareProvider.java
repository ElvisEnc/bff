package bg.com.bo.bff.providers.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorMiddlewareProvider {
    private Integer code;
    private String errorType;
    private List<ErrorDetailProvider> errorDetailResponse;

    @Data
    @Builder
    public static class ErrorDetailProvider {
        private String code;
        private String description;
    }
}
