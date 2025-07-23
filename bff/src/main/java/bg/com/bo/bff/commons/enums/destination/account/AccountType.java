package bg.com.bo.bff.commons.enums.destination.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {
    CASB("CCAD", "Cuenta corriente / Caja de ahorro"),
    CARD("CTARJETAD", "Tarjeta de crédito"),
    MOBILE("CMOVILD", "Billetera móvil"),
    LOAN("CPRESTAMOD", "Préstamo");

    private final String code;
    private final String description;
}
