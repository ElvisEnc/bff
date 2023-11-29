package bg.com.bo.bff.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Schema(example = "110011245", description = "Este es el código de persona del cliente ganamóvil")
    private int codigoPersona;
}
