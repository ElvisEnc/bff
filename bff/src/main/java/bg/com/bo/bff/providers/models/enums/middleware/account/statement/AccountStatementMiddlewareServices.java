package bg.com.bo.bff.providers.models.enums.middleware.account.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatementMiddlewareServices {

    POST_ACCOUNT_STATEMENT("/bs/v1/accounts/reports/generate-basic");

    private final String serviceURL;
}
