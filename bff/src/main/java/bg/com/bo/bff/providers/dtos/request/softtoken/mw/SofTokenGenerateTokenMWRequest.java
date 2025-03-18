package bg.com.bo.bff.providers.dtos.request.softtoken.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SofTokenGenerateTokenMWRequest {
    private String personId;
    private String codCanal;
    private String deviceId;
}
