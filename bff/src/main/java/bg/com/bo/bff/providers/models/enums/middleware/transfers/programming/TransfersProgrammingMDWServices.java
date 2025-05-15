package bg.com.bo.bff.providers.models.enums.middleware.transfers.programming;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransfersProgrammingMDWServices {


    GET_TRANSFERS_LIST("/bs/v1/persons/%s"),
    GET_PAYMENTS_PLAN("/bs/v1/transfers/%s")
    ;

    private final String serviceUrl;

}
