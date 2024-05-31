package bg.com.bo.bff.providers.dtos.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddWalletAccountBasicRequest {
    private String personId;
    private String companyPersonId;
    private String toAccountNumber;
    private String reference;
    private String isFavorite;
}
