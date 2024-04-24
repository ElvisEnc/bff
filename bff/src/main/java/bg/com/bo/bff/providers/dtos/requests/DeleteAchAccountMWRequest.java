package bg.com.bo.bff.providers.dtos.requests;

@lombok.Data
public class DeleteAchAccountMWRequest {
    private String personId;
    private String identifier;
}
