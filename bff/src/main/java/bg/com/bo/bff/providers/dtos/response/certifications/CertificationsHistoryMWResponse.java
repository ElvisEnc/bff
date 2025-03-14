package bg.com.bo.bff.providers.dtos.response.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationsHistoryMWResponse {

    private List<HistoricMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HistoricMW {
        private String requestNumber;
        private String day;
        private String month;
        private String year;
        private String title;
        private String docNumber;
        private String state;
        private String mail;
    }

}
