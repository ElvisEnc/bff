package bg.com.bo.bff.application.dtos.response.destination.account;

import bg.com.bo.bff.providers.dtos.response.ach.account.mw.BankMW;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bank {
    @Schema(example = "Banco Ganadero", description = "Descripción de entidad financiera")
    private String description;
    @Schema(example = "1782", description = "Codigo de entidad financiera")
    private String code;


    public static Bank fromMWBank(BankMW bankMW) {
        return new Bank(bankMW.getDescription(), bankMW.getCode());
    }
}