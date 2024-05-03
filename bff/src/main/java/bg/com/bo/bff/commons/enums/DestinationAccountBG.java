package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DestinationAccountBG {
    BG( "Banco Ganadero S.A."),
    YOLO( "YOLO Pago");

    private final String name;
}
