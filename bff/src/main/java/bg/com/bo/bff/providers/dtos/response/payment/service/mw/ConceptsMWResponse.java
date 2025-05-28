package bg.com.bo.bff.providers.dtos.response.payment.service.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConceptsMWResponse {

    private List<ConceptItem> data;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConceptItem{
        private String concept;
        private String description;
        private String abbreviation;
    }

}
