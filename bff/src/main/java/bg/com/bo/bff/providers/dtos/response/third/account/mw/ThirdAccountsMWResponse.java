package bg.com.bo.bff.providers.dtos.response.third.account.mw;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdAccountsMWResponse {
    private List<ThirdAccountMW> data;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThirdAccountMW{
        private String identifier;
        private String accountId;
        private String accountNumber;
        private String currencyCode;
        private String currencyAcronym;
        private String accountType;
        private String accountTypeAbbreviation;
        private String clientName;
        private String accountAliases;
        private String isFavorite;
    }
}
