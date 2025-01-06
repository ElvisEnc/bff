package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeError {
    TRANSACTION_NOT_ALLOWED("TRANSACTION_NOT_ALLOWED","Transaccion en moneda extranjera está temporalmente deshabilitada"),
    TRANSFER_DUPLICATE("TRANSFER_DUPLICATE","Transferencia duplicada"),
    INVALID_PARAMS("INVALID_PARAMS","Error en los parametros, pueden ser los del header o path"),
    INVALID_DATA("INVALID_DATA","Datos invalidos, ya sea desde el front o postman"),
    INVALID_USER_DATA("INVALID_USER_DATA","Datos invalidos del usuario, ya sea desde el front o postman"),
    ERROR_PROCEDURE("ERROR_PROCEDURE","Error ocurrido en el proceso");

    private final String code;
    private final String description;
}
