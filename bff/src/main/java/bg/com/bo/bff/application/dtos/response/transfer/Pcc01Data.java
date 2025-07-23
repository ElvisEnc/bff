package bg.com.bo.bff.application.dtos.response.transfer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pcc01Data {

    @Schema(example = "S", description = "Valor que indica si requiere o no el control")
    private String requiresPcc01;
}
