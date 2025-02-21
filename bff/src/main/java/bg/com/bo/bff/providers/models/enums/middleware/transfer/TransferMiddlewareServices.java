package bg.com.bo.bff.providers.models.enums.middleware.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransferMiddlewareServices {
    TRANSFER_TO_OWN_ACCOUNT("/bs/v1/transfer/same-bank/to-own-account"),
    TRANSFER_TO_WALLET_ACCOUNT("/bs/v1/transfer/same-bank/to-yolo-accounts"),
    TRANSFER_TO_THIRD_ACCOUNT("/bs/v1/transfer/same-bank/to-other-accounts"),
    TRANSFER_TO_ACH_ACCOUNT("/bs/v1/ach/transfers"),
    VALIDATE_PCC01("/bs/v1/money-laundering/validate-digital");

    private final String serviceURL;
}
