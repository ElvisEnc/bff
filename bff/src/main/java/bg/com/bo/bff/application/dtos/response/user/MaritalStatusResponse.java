package bg.com.bo.bff.application.dtos.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaritalStatusResponse {
    private List<MaritalStatus> data;
}
