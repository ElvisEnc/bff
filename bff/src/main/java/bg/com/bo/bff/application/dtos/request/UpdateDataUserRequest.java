package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.application.dtos.response.user.UpdatePersonalDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateDataUserRequest {
    @Valid
    @Schema(description = "Estado civil del usuario")
    private UpdatePersonalDetail.MaritalStatus maritalStatus;

    @Valid
    @Schema(description = "Actividad económica del usuario")
    private UpdatePersonalDetail.EconomicalActivity economicalActivity;
    @Valid
    @Schema(description = "Datos personales del usuario")
    private UpdatePersonalDetail.PersonalData personalData;
    @Schema(description = "Referencias del usuario")
    private UpdatePersonalDetail.Reference reference;
}
