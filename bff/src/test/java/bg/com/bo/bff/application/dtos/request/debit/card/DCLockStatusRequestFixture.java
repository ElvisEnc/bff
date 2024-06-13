package bg.com.bo.bff.application.dtos.request.debit.card;

public class DCLockStatusRequestFixture {
    public static DCLockStatusRequest withDefault() {
        return DCLockStatusRequest.builder()
                .type(Integer.valueOf("0"))
                .build();
    }
}
