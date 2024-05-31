package bg.com.bo.bff.providers.dtos.response;

import bg.com.bo.bff.models.ThirdAccount;

import java.util.List;

@lombok.Data
public class ThirdAccountListMWResponse {
    private List<ThirdAccount> data;
}
