package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.application.dtos.response.user.PersonalDetail;
import bg.com.bo.bff.application.dtos.response.user.UpdatePersonalDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateDataUserRequest {
    @Schema(description = "Estado civil del usuario")
    private UpdatePersonalDetail.MaritalStatus maritalStatus;

    @Schema(description = "Actividad económica del usuario")
    private UpdatePersonalDetail.EconomicalActivity economicalActivity;

    @Schema(description = "Datos personales del usuario")
    private UpdatePersonalDetail.PersonalData personalData;

    @Schema(description = "Referencias del usuario")
    private List<UpdatePersonalDetail.Reference> references;
}
