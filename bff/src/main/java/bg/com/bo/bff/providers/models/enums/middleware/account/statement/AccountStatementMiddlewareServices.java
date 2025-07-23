package bg.com.bo.bff.providers.models.enums.middleware.account.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatementMiddlewareServices {

    POST_ACCOUNT_STATEMENT("/bs/v1/accounts/reports/generate-basic"),
    REPORT_TRANSFERS("/bs/v1/accounts/reports/transfer"),
    REGENERATE_VOUCHER("/bs/v1/accounts/reports/voucher")
    ;

    private final String serviceURL;
}
