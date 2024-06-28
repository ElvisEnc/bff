package bg.com.bo.bff.providers.dtos.request.payment.services;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAffiliateServiceMWRequest {
    private String personId;
    private String affiliationCode;
}


