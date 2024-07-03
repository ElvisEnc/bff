package bg.com.bo.bff.application.dtos.response.dpf;

import java.util.List;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class DpfListResponse {
    private List<DpfDataResponse> data;
}
