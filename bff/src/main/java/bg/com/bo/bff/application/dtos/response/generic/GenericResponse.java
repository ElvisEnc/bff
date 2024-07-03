package bg.com.bo.bff.application.dtos.response.generic;

import bg.com.bo.bff.commons.enums.response.IGenericControllerResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class GenericResponse {

    @Schema(example = "SUCCESS", description = "Status code de la operación.")
    private String code;

    @Schema(example = "Ejecutado satisfactoriamente.", description = "Mensaje de la operación.")
    private String message;

    public static GenericResponse instance(IGenericControllerResponse genericControllerResponse) {
        return GenericResponse.builder()
                .code(genericControllerResponse.getCode())
                .message(genericControllerResponse.getMessage())
                .build();
    }
}
