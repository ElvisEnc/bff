package bg.com.bo.bff.application.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtractDataResponse {
    private List<ExtractResponse> data;

    @lombok.Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtractResponse {
        private String status;
        private String type;
        private Double amount;
        private String currency;
        private String channel;
        private String dateMov;
        private String timeMov;
        private Double movBalance;
        private String seatNumber;
        private String description;
    }
}
