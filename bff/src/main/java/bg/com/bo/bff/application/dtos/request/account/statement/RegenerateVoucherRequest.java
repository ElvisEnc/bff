package bg.com.bo.bff.application.dtos.request.account.statement;

import bg.com.bo.bff.commons.annotations.DatePattern;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegenerateVoucherRequest {

    @OnlyNumber
    private String seatNumber;

    @OnlyNumber
    private String branchOfficeCode;

    @DatePattern
    private String date;

}
