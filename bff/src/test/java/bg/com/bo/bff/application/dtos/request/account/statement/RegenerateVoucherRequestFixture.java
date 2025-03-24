package bg.com.bo.bff.application.dtos.request.account.statement;

public class RegenerateVoucherRequestFixture {

    public static RegenerateVoucherRequest withDefaults(){
        return RegenerateVoucherRequest.builder()
                .branchOfficeCode("11")
                .seatNumber("123456789")
                .date("2025-01-01")
                .build();
    }

}
