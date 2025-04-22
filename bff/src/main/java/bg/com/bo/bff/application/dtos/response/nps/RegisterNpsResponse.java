package bg.com.bo.bff.application.dtos.response.nps;

import bg.com.bo.bff.providers.dtos.response.nps.mw.NpsMW;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterNpsResponse {

    private int npsId;
    private String codeError;
    private String message;
    private List<NpsMW> questionIds;


}
