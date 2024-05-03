package bg.com.bo.bff.application.dtos.response;

import java.util.List;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class DPFListResponse {
    private List<DPFDataResponse> data;
}
