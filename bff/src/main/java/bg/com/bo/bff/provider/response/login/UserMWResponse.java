package bg.com.bo.bff.provider.response.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMWResponse {
    @Schema(example = "110011245", description = "Este es el código de persona del cliente ganamóvil")
    private String personId;
}
