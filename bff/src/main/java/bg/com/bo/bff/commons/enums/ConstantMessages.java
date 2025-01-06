package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConstantMessages {
    GENERIC("Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    ACH_DUPLICATE("Su transacción no se registró debido a que existe otra trasacción similar ingresada recitemente. Favor revisar el Reporte transferencias a Otros Bancos", "Tranferencia Duplicada");

    private final String message;
    private final String title;
}
