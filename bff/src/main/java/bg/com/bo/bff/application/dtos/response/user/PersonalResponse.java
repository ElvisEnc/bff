package bg.com.bo.bff.application.dtos.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalResponse {

    @Schema(description = "Estado civil del usuario")
    private PersonalDetail.MaritalStatus maritalStatus;

    @Schema(description = "Actividad económica del usuario")
    private PersonalDetail.EconomicalActivity economicalActivity;

    @Schema(description = "Datos personales del usuario")
    private PersonalDetail.PersonalData personalData;

    @Schema(description = "Referencias del usuario")
    private List<PersonalDetail.Reference> references;

}
