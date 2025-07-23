package bg.com.bo.bff.providers.models.enums.middleware.credit.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreditCardConstans {
    BLOCK_TYPE("2", "bloqueo"),
    UNBLOCK_TYPE("0", "desbloqueo"),
    BLOCK_REASON("4", "bloqueo"),
    UNBLOCK_REASON("0", "desbloqueo"),
    SUCCESS_BLOCK("00005", "código success"),
    PERSON_ID("PersonId", "string para TC"),
    ACCOUNT_ID("AccountId", "string para TC"),
    TC_ACCOUNT_ID("TcAccountId", "string para TC"),
    TRANSACTION_TYPE("2", "tipo de transaccion para TC"),
    APPROVED("APPROVED", "estado aprobado del pago"),
    SUCCESS_AUTHORIZATION("TJC092", "código success");

    private final String value;
    private final String description;
}
