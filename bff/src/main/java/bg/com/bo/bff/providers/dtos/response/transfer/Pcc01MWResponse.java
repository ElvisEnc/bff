package bg.com.bo.bff.providers.dtos.response.transfer;

import bg.com.bo.bff.application.dtos.response.transfer.Pcc01Data;
import lombok.ToString;

@lombok.Data
@ToString
public class Pcc01MWResponse {
    private Pcc01Data data;
}
