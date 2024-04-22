package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;
import bg.com.bo.bff.providers.interfaces.IAccountStatementCsvProvider;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;

@Service
public class AccountStatementCsvProvider implements IAccountStatementCsvProvider {

    private static final Logger LOGGER = LogManager.getLogger(AccountStatementCsvProvider.class.getName());

    @Override
    public byte[] generateCsv(List<AccountReportBasicResponse.AccountReportData> basicResponseData) {
        try {
            StringWriter writer = new StringWriter();

            String[] title = {"Estado", "Tipo", "Monto", "Moneda", "Canal", "Fecha", "Hora", "Balance", "Transacción", "Descripción"};
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(title).withDelimiter(';').withAutoFlush(true));

            if (!basicResponseData.isEmpty())
                for (AccountReportBasicResponse.AccountReportData data : basicResponseData) {
                    csvPrinter.printRecord(data.getStatus(), data.getMoveType(), data.getAmount(), data.getCurrencyCod(), data.getBranchOffice(), data.getProcessDate(), data.getAccountingTime(), data.getCurrentBalance(), data.getSeatNumber(), data.getDescription().replace("\n", "").replace("\r", ""));
                }
            else {
                String empty = "Sin registro";
                csvPrinter.printRecord(empty, empty, empty, empty, empty, empty, empty, empty, empty, empty);
            }
            csvPrinter.close();

            byte[] csvBytes = writer.toString().getBytes();
            return csvBytes;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e);
            throw new GenericException();
        }
    }
}
