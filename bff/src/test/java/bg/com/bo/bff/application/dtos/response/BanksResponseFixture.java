package bg.com.bo.bff.application.dtos.response;

import bg.com.bo.bff.application.dtos.Bank;

import java.util.List;

public class BanksResponseFixture {
    public static BanksResponse withDefault(){
        return new BanksResponse(
                List.of(
                        new Bank("ADM TARJETAS DE CREDITO A.T.C","01111"),
                        new Bank("BANCO BISA","01112")
                )
        );
    }
}
