package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorExceptions {

    ERROR_REQUEST_TOKEN("Hubo un error no controlado al realizar el requestToken"),
    ERROR_CLIENT_TOKEN("Hubo un error no controlado al crear el clienteToken"),
    ERROR_THIRD_ACCOUNT("Hubo un error no controlado al realizar el getListThridAccounts"),
    ERROR_CLIENT("Hubo un error no controlado al crear el clienteGetAccounts");

    private final String message;
}
