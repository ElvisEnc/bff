package bg.com.bo.bff.application.dtos.response.registry;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BffHandshakeResponse {
    @Schema(description = "Key de encriptación.")
    private String key;
    @Schema(description = "Id de key.")
    private String eki;
    @Schema(description = "Expiración del key.")
    private Long expireAt;
}
