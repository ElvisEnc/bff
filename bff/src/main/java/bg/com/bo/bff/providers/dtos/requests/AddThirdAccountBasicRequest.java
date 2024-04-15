package bg.com.bo.bff.providers.dtos.requests;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddThirdAccountBasicRequest {
    private String personId;
    private String companyPersonId;
    private String toAccountNumber;
    private String reference;
    private String isFavorite;
}
