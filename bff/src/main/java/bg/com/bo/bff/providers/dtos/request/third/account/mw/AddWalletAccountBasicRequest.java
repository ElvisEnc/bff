package bg.com.bo.bff.providers.dtos.request.third.account.mw;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddWalletAccountBasicRequest {
    private String personId;
    private String companyPersonId;
    private String toAccountNumber;
    private String reference;
    private String isFavorite;
}
