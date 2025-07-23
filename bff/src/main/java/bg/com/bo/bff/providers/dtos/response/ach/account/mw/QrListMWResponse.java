package bg.com.bo.bff.providers.dtos.response.ach.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrListMWResponse {
    private List<QrGeneratedPaidMW> data;
}
