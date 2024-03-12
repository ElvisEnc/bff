package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CanalMW {
    GANANET("1", "GANANET"),
    GANAMOVIL("2", "GANAMOVIL"),
    WEB("3", "WEB"),
    KIOSCO("4", "KIOSCO"),
    TOPAZ("5", "TOPAZ"),
    GANAMOVIL_EMPRESA("6", "GANAMOVIL EMPRESA"),
    BILLETERA("7", "BILLETERA");

    private final String canal;
    private final String description;
}
