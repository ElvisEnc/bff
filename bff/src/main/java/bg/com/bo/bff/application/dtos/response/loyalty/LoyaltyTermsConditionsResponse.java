package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyTermsConditionsResponse {
    @Schema(description = "Nombre del contrato")
    @JsonProperty("contractName")
    private String contractName;

    @Schema(description = "Contrato")
    @JsonProperty("contract")
    private String contract;

    @Schema(description = "Informacion de la persona")
    @JsonProperty("informationPerson")
    private PersonData informationPerson;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonData {
        @Schema(description = "Numero de documento de la persona")
        @JsonProperty("documentNumber")
        private String documentNumber;

        @Schema(description = "Tipo de documento de la persona")
        @JsonProperty("documentType")
        private String documentType;

        @Schema(description = "Nombre de la persona")
        @JsonProperty("namePerson")
        private String namePerson;
    }
}
