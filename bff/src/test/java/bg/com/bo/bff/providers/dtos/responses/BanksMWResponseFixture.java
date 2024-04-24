package bg.com.bo.bff.providers.dtos.responses;

import bg.com.bo.bff.models.dtos.BankMW;
import bg.com.bo.bff.models.dtos.BanksMWResponse;

import java.util.List;

public class BanksMWResponseFixture {
    public static BanksMWResponse withDefault(){
        return new BanksMWResponse(
                List.of(
                        new BankMW("ADM TARJETAS DE CREDITO A.T.C","01111","ADM"),
                        new BankMW("BANCO BISA","01112","BISA")
                )
        );
    }
}
