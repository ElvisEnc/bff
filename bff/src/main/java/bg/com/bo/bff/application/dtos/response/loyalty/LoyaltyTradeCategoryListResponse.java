package bg.com.bo.bff.application.dtos.response.loyalty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyTradeCategoryListResponse {
    private List<LoyaltyTradeCategoryResponse> data;
}
