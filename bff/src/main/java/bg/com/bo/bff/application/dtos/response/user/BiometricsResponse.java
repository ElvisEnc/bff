package bg.com.bo.bff.application.dtos.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BiometricsResponse {
    private Boolean status;
    private String authenticationType;
}

