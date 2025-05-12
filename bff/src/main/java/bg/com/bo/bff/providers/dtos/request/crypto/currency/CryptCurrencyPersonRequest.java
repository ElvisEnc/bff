package bg.com.bo.bff.providers.dtos.request.crypto.currency;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptCurrencyPersonRequest {

    @JsonProperty("personId")
    @JsonAlias({"nroPersona", "personNumber"})
    private String personId;
}
