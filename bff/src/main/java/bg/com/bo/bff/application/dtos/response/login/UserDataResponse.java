package bg.com.bo.bff.application.dtos.response.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataResponse {
    @Schema(example = "Jorge", description = "Nombre del usuario.")
    private String name;
    @Schema(example = "Jorge Perez", description = "Nombre completo del usuario.")
    private String fullName;
    @Schema(example = "1", description = "Person ID del usuario.")
    private String personId;
    @Schema(example = "1", description = "User Device Id")
    private String userDeviceId;
    @Schema(example = "1", description = "Role Person Id")
    private String rolePersonId;
}
