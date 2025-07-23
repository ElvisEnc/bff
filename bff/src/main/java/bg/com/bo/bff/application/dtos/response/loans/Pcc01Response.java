package bg.com.bo.bff.application.dtos.response.loans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pcc01Response {
    @Schema(example = "S", description = "Valor que indica si requiere o no el control")
    private String requiresPcc01;
}
