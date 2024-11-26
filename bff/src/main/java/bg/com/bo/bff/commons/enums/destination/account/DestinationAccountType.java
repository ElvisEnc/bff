package bg.com.bo.bff.commons.enums.destination.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DestinationAccountType {
    CUENTA_TERCERO(1, "CUENTA DE TERCEROS"),
    CUENTA_ACH(2, "CUENTAS ACH"),
    BILLETERA(3, "BILLETERA YOLO");

    private final Integer code;
    private final String description;
}
