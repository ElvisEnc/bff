package bg.com.bo.bff.providers.dtos.responses.personal.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EconomyActivity {

    @JsonProperty("EMPRESA")
    private String company;

    @JsonProperty("CARGO")
    private String position;

    @JsonProperty("FUENTE_INGRESO")
    private String incomeSource;
}
