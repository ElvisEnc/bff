package bg.com.bo.bff.application.dtos.response.destination.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidateAccountResponse {
    private Data data;

    @AllArgsConstructor
    @NoArgsConstructor
    @lombok.Data
    public static class Data {
        private String accountId;
        private String accountNumber;
        private String codClient;
        private String clientName;
        private String currencyCod;
        private String branchCode;
    }

    public ValidateAccountResponse(String accountId,
                                   String accountNumber,
                                   String codClient,
                                   String clientName,
                                   String currencyCod,
                                   String branchCode) {
        this.data = new Data(
                accountId,
                accountNumber,
                codClient,
                clientName,
                currencyCod,
                branchCode

        );
    }
}


