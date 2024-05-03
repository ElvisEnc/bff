package bg.com.bo.bff.application.dtos.response.destination.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationAccount {
            private Long id;
            private Long accountId;
            private Long accountNumber;
            private String currencyCode;
            private String currencyAcronym;
            private String clientName;
            private String bankCode;
            private String bankName;
            private String accountAliases;
            private Integer destinationAccountType;
}
