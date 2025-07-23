package bg.com.bo.bff.providers.dtos.response.softtoken.mw;
 
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoftTokenQuestionEnrollmentMWResponse {
    private List<SoftTokenQuestionEnrollmentMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SoftTokenQuestionEnrollmentMW {
        private String question;
        private String format;
        private String textHelp;
    }
}
