package bg.com.bo.bff.commons.enums.destination.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DestinationAccountBG {
    BG( "Banco Ganadero S.A."),
    YOLO( "YOLO Pago"),
    THIRD("Third"),
    WALLET("Wallet"),
    ACH("ACH");

    private final String name;
}
