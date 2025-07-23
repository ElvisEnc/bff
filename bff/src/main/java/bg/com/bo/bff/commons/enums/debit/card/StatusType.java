package bg.com.bo.bff.commons.enums.debit.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusType {
    UNLOCKED("0", "Tarjeta activa sin bloqueo"),
    PERMANENT_LOCKED("4", "Tarjeta bloqueada de forma permanente"),
    TEMPORAL_LOCKED("5", "Tarjeta bloqueada de forma temporal"),
    FRAUD_LOCKED("7", "Tarjeta bloqueada por fraude"),;

    private final String code;
    private final String description;

    public static String getDescriptionByCode(String code) {
        for (StatusType status : values()) {
            if (status.getCode().equals(code)) {
                return status.getDescription();
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
