package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyGetTermsConditionsResponse {
    @JsonProperty("nombreContrato")
    private String contractName;

    @JsonProperty("contrato")
    private String contract;

    @JsonProperty("informacionPersona")
    private Person informationPerson;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Person {
        @JsonProperty("numeroDocumento")
        private String documentNumber;

        @JsonProperty("tipoDocumento")
        private String documentType;

        @JsonProperty("nombre")
        private String namePerson;
    }
}
