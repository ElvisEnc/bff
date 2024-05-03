package bg.com.bo.bff.models;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThirdAccountListResponse {
    private List<ThirdAccount> data;
}
