package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.providers.dtos.requests.UpdateTransactionLimitMWRequest;

public class UpdateTransactionLimitMWRequestFixture {
    public static UpdateTransactionLimitMWRequest withDefault(){
        return new UpdateTransactionLimitMWRequest("1000","1");
    }
}
