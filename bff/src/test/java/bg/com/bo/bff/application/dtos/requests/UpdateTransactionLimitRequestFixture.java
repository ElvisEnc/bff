package bg.com.bo.bff.application.dtos.requests;

import bg.com.bo.bff.application.dtos.request.UpdateTransactionLimitRequest;

public class UpdateTransactionLimitRequestFixture {
    public static UpdateTransactionLimitRequest withDefault(){
        return new UpdateTransactionLimitRequest("1000","1");
    }
}
