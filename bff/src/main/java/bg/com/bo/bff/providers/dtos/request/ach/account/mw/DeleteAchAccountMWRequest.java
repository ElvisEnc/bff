package bg.com.bo.bff.providers.dtos.request.ach.account.mw;

import lombok.Data;

@Data
public class DeleteAchAccountMWRequest {
    private String personId;
    private String identifier;
}
