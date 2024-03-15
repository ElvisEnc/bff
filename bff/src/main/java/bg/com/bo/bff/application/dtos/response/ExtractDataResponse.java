package bg.com.bo.bff.application.dtos.response;

import java.util.List;

@lombok.Data
public class ExtractDataResponse {
    private List<ExtractResponse>  data;

    @lombok.Data
    public static class ExtractResponse {
        private String status;
        private String type;
        private Double amount;
        private String currency;
        private String channel;
        private String dateMov;
        private String timeMov;
        private Double movBalance;
    }
}
