package bg.com.bo.bff.providers.dtos.request.own.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegenerateVoucherMWRequest {

    private String seatNumber;
    private String codBranchOffice;
    private String processDate;

}
