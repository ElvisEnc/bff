package bg.com.bo.bff.providers.dtos.response.crypto.currency;

import bg.com.bo.bff.models.ClientToken;

import java.util.List;

public class CryptoCurrencyResponseDTOFixture {

    public static ClientToken withDefaultClientToken() {
        ClientToken clientToken = new ClientToken();
        clientToken.setAccessToken("token");
        return clientToken;
    }

    public static CryptoCurrencyPostRegisterAccountResponse withDefaultRegisterAccount() {
        return CryptoCurrencyPostRegisterAccountResponse.builder()
                .codeError("COD000")
                .message("¡Apertura de cuenta exitosa!")
                .build();
    }

    public static CryptoCurrencyPostRegisterAccountResponse withDefaultRegisterAccountError() {
        return CryptoCurrencyPostRegisterAccountResponse.builder()
                .codeError("COD002")
                .message("¡Apertura de cuenta exitosa!")
                .build();
    }

    public static CryptoCurrencyGetAvailableBalanceResponse  withDefaultAvailableBalance() {
        return CryptoCurrencyGetAvailableBalanceResponse.builder()
                .codeError("COD000")
                .message("ok")
                .data(
                        CryptoCurrencyGetAvailableBalanceResponse.GetBalanceResponse.builder()
                                .currency("222")
                                .availableBalance(7.4)
                                .account(123456)
                                .status("ACTIVO")
                                .jtsOid(987654)
                                .product("PRODUCTO_X")
                                .codeError("0")
                                .description("Consulta exitosa")
                                .build()
                )
                .build();
    }

    public static CryptoCurrencyGetAvailableBalanceResponse  withDefaultAvailableBalanceError() {
        return CryptoCurrencyGetAvailableBalanceResponse.builder()
                .codeError("COD002")
                .message("error")
                .build();
    }

    public static CryptoCurrencyGetAccountEmailResponse  withDefaultAccountEmail() {
        return CryptoCurrencyGetAccountEmailResponse.builder()
                .codeError("COD000")
                .message("ok")
                .data(
                        CryptoCurrencyGetAccountEmailResponse.GetAccountEmailResponse.builder()
                                .email("TEST")
                                .name("TEST")
                                .codeError("TEST")
                                .message("TEST")
                                .build()
                )
                .build();
    }

    public static CryptoCurrencyGetAccountEmailResponse  withDefaultAccountEmailError() {
        return CryptoCurrencyGetAccountEmailResponse.builder()
                .codeError("COD002")
                .message("error")
                .build();
    }

    public static CryptoCurrencyExchangeRateResponse withDefaultExchangeRate() {
        return CryptoCurrencyExchangeRateResponse.builder()
                .codeError("COD000")
                .message("ok")
                .data(
                        CryptoCurrencyExchangeRateResponse.ExchangeResponse.builder()
                                .purchaseFxRate(78.5)
                                .saleFxRate(78.5)
                                .description("TEST")
                                .code("TEST")
                                .messageError("TEST")
                                .build()
                )
                .build();
    }

    public static CryptoCurrencyExchangeRateResponse withDefaultExchangeRateError() {
        return CryptoCurrencyExchangeRateResponse.builder()
                .codeError("COD002")
                .message("error")
                .build();
    }

    public static CryptoCurrencyExchangeOperationResponse withDefaultExchangeOperation() {
        return CryptoCurrencyExchangeOperationResponse.builder()
                .codeError("COD000")
                .message("ok")
                .data(
                        CryptoCurrencyExchangeOperationResponse.OperationResponse.builder()
                                .importDebited(7.8)
                                .currency(0)
                                .seatNo(78L)
                                .receiptId(78)
                                .tcCredit(78)
                                .tcDebit(7.4)
                                .branch(70)
                                .dateSeat("05/05/2025")
                                .importItf(78.2)
                                .importAccredited(78.2)
                                .build()
                )
                .build();
    }

    public static CryptoCurrencyExchangeOperationResponse withDefaultExchangeOperationError() {
        return CryptoCurrencyExchangeOperationResponse.builder()
                .codeError("COD002")
                .message("error")
                .build();
    }

    public static CryptoCurrencyGenerateVoucherResponse  withDefaultCurrencyGenerateVoucher() {
        return CryptoCurrencyGenerateVoucherResponse .builder()
                .codeError("COD000")
                .message("ok")
                .data(
                        CryptoCurrencyGenerateVoucherResponse.VoucherResponse.builder()
                                .transactionNumber(1001)
                                .transactionType(1)
                                .amount(500.0)
                                .currencyAmount(500)
                                .currencyEquivalentCredit(480)
                                .currencyEquivalentDebit(520)
                                .senderName("John Doe")
                                .senderAccountNumber("1234567890")
                                .senderNdJts(11111)
                                .receiverName("Jane Smith")
                                .receiverNdJts(22222)
                                .receiverAccountNumber("0987654321")
                                .receiverBank("Bank XYZ")
                                .equivalentDebitAmount(520.0)
                                .debitExchangeRate(1.04)
                                .equivalentCreditAmount(480.0)
                                .creditExchangeRate(0.96)
                                .description("Transferencia test")
                                .build()
                )
                .build();
    }

    public static CryptoCurrencyGenerateVoucherResponse withDefaultCurrencyGenerateVoucherError() {
        return CryptoCurrencyGenerateVoucherResponse.builder()
                .codeError("COD002")
                .message("error")
                .build();
    }

    public static CryptoCurrencyAccountExtractResponse  withDefaultAccountExtract() {
        CryptoCurrencyAccountExtractResponse.ExtractResponse extract = CryptoCurrencyAccountExtractResponse.ExtractResponse.builder()
                .existsVoucher(true)
                .transactionDate("2025-05-16")
                .transactionTime("14:30:00")
                .amount("150.00")
                .description("Depósito test")
                .day(16)
                .month("MAY")
                .year("2025")
                .transactionType("DEPÓSITO")
                .processDate("2025-05-16")
                .branch("Sucursal Central")
                .seatNumber("001")
                .correlative("000123")
                .currentBalance("500.00")
                .currencySymbol("$")
                .build();
        return CryptoCurrencyAccountExtractResponse.builder()
                .codeError("COD000")
                .message("ok")
                .data(List.of(extract))
                .build();
    }

    public static CryptoCurrencyAccountExtractResponse  withDefaultAccountExtractNull() {
        return CryptoCurrencyAccountExtractResponse.builder()
                .codeError("COD000")
                .message("ok")
                .data(null)
                .build();
    }

    public static CryptoCurrencyAccountExtractResponse withDefaultAccountExtractError() {
        return CryptoCurrencyAccountExtractResponse.builder()
                .codeError("COD002")
                .message("error")
                .build();
    }
}
