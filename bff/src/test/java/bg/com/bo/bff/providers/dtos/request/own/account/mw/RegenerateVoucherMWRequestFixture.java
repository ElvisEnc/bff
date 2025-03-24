package bg.com.bo.bff.providers.dtos.request.own.account.mw;

public class RegenerateVoucherMWRequestFixture {

    public static RegenerateVoucherMWRequest withDefaults(){
        return RegenerateVoucherMWRequest.builder()
                .seatNumber("123456789")
                .codBranchOffice("1")
                .processDate("2025-01-01")
                .build();
    }

}
