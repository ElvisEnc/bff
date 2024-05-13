package bg.com.bo.bff.providers.mappings.transfer;

import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferDetailsMapper {
    private String title;
    private String amount;
    private String currency;
    private String currencyAmount;
    private String description;
    private String dateHour;
    private String fromTitle;
    private String fromHolder;
    private String fromAccount;
    private String toTitle;
    private String toHolder;
    private String toAccount;
    private String nroEntry;

    public TransferDetailsMapper(TransferResponseMD response) {
        this.title = "¡Transferencia exitosa!";
        this.amount = String.valueOf(response.getData().getAmount());
        this.currency = response.getData().getCurrency();
        this.currencyAmount = currency + " " + amount;
        this.description = response.getData().getDescription();
        this.dateHour = response.getData().getAccountingDate() + " a las " + response.getData().getAccountingTime();
        this.fromTitle = "CUENTA DE ORIGEN";
        this.fromHolder = response.getData().getFromHolder();
        this.fromAccount = response.getData().getFromAccountNumber();
        this.toTitle = "CUENTA DE DESTINO";
        this.toHolder = response.getData().getToHolder();
        this.toAccount = response.getData().getToAccountNumber();
        this.nroEntry = "Nro. " + response.getData().getAccountingEntry();
    }
}
