package bg.com.bo.bff.application.dtos.response.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataResponse {
    @Schema(example = "1", description = "Person ID del usuario.")
    private String personId;
    @Schema(example = "1")
    private String documentNumber;
    @Schema(example = "1", description = "User Device Id")
    private String userDeviceId;
    @Schema(example = "1", description = "Role Person Id")
    private String rolePersonId;
}
