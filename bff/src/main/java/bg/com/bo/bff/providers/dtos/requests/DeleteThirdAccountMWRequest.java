package bg.com.bo.bff.providers.dtos.requests;

@lombok.Data
public class DeleteThirdAccountMWRequest {
    private String personId;
    private String identifier;
    private String accountNumber;
}
