package bg.com.bo.bff.commons.enums;

import lombok.Getter;

@Getter
public enum Currency {
    BOB("Bs", "0", "Bolivianos"),
    USD("$us","2225", "Dólares"),
    EUR("EU", "1112", "Euros"),
    UFV("UFV","9800", "UFV");

    private final String symbol;
    private final String code;
    private final String title;

    Currency(String symbol, String code, String title) {
        this.symbol = symbol;
        this.code = code;
        this.title = title;
    }
}
