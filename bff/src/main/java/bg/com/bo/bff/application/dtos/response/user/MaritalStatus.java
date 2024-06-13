package bg.com.bo.bff.application.dtos.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaritalStatus {
    @Schema(description = "Id del item")
    private String id;

    @Schema(description = "Key del item")
    private String key;

    @Schema(description = "Descripción del item")
    private String description;
}
