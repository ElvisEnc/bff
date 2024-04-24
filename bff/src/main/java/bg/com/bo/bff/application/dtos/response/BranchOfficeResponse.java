package bg.com.bo.bff.application.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BranchOfficeResponse {
    private List<BranchOfficeDataResponse> data;
}
