package bg.com.bo.bff.providers.dtos.response.third.account.mw;

import bg.com.bo.bff.application.dtos.response.destination.account.ThirdAccount;

import java.util.List;

@lombok.Data
public class ThirdAccountListMWResponse {
    private List<ThirdAccount> data;
}
