package bg.com.bo.bff.application.dtos.response.transfer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

@lombok.Data
@ToString
public class Pcc01Data {

    @Schema(example = "S", description = "Valor que indica si requiere o no el control")
    private String requiresPcc01;
}
