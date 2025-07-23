package bg.com.bo.bff.providers.dtos.response.payment.service.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AffiliateCriteriaMWResponse {
    @JsonProperty("data")
    private DetailData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailData {

        @JsonProperty("serviceCode")
        private String serviceCode;

        @JsonProperty("year")
        private int year;

        @JsonProperty("subServices")
        private List<SubService> subServices;

        @JsonProperty("searchCriteria")
        private List<SearchCriteria> searchCriteria;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SubService {
            @JsonProperty("ETIQUETA_CRITERIO")
            private String labelCriteria;

            @JsonProperty("ABREVIATURA")
            private String abbreviation;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SearchCriteria {

            @JsonProperty("labelCriteria")
            private String labelCriteria;

            @JsonProperty("description")
            private String description;

            @JsonProperty("searchCriteriaId")
            private String searchCriteriaId;

            @JsonProperty("searchCriteriaIdAbbreviation")
            private String searchCriteriaIdAbbreviation;

            @JsonProperty("fields")
            private List<Field> fields;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Field {

                @JsonProperty("identifier")
                private int identifier;

                @JsonProperty("label")
                private String label;

                @JsonProperty("description")
                private String description;

                @JsonProperty("abbreviation")
                private String abbreviation;

                @JsonProperty("isMandatory")
                private String isMandatory;

                @JsonProperty("minimumLength")
                private String minimumLength;

                @JsonProperty("maximumLength")
                private String maximumLength;

                @JsonProperty("dataTypeCode")
                private String dataTypeCode;

                @JsonProperty("values")
                private List<Value> values;

                @Data
                @Builder
                @NoArgsConstructor
                @AllArgsConstructor
                public static class Value {
                    @JsonProperty("code")
                    private String code;

                    @JsonProperty("description")
                    private String description;
                }
            }
        }
    }
}
