package bg.com.bo.bff.application.dtos.response.nps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NpsResponse {

    private String codeError;
    private String message;
}
