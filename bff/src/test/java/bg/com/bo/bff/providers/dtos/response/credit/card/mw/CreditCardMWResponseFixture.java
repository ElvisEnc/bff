package bg.com.bo.bff.providers.dtos.response.credit.card.mw;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreditCardMWResponseFixture {
    public static ListCreditCardMWResponse withDefaultListCreditCardMWResponse() {
        return ListCreditCardMWResponse.builder()
                .creditCards(Collections.singletonList(
                        createCreditCardMWResponse()
                ))
                .prepaidCards(Collections.singletonList(
                        createPrepaidCardMWResponse()
                ))
                .build();
    }

    public static ListCreditCardMWResponse.CreditCardMWResponse createCreditCardMWResponse() {
        return ListCreditCardMWResponse.CreditCardMWResponse.builder()
                .accountMaster("123")
                .product("Visa")
                .panNumber("4111111111111111")
                .cmsAccountNumber("CMS12345")
                .holderName("John Doe")
                .currency("068")
                .currencyDescription("BOB")
                .dueDate("2024-12-31")
                .limitAmount("5000")
                .typeCard("Credit")
                .clientCode("C12345")
                .build();
    }

    public static ListCreditCardMWResponse.PrepaidCardMWResponse createPrepaidCardMWResponse() {
        return ListCreditCardMWResponse.PrepaidCardMWResponse.builder()
                .accountMaster("123")
                .product("Mastercard")
                .panNumber("5500000000000004")
                .cmsAccountNumber("CMS54321")
                .holderName("Jane Doe")
                .currency("840")
                .currencyDescription("USD")
                .solicitudeDate("2023-01-15")
                .typeCardCode("P123")
                .dueDate("2025-01-01")
                .status("Active")
                .typeCard("Prepaid")
                .clientCode("C54321")
                .build();
    }


    public static DetailsCreditCardMWResponse withDefaultDetailsCreditCardMWResponse() {
        return DetailsCreditCardMWResponse.builder()
                .accountMaster("123")
                .cmsAccountNumber("987654321")
                .panNumber("1234-5678-9012-3456")
                .holderName("John Doe")
                .type("Credit")
                .placeCode("001")
                .place("Main Branch")
                .amountLimit("5000")
                .amountAvailable("2000")
                .currencyCode("USD")
                .currencyDescription("US Dollar")
                .dueDate("2024-12-31")
                .openDate("2022-01-01")
                .registrationDate("2022-01-01")
                .statusCard("Active")
                .statusAccount("Active")
                .insurance("S")
                .limMin("100")
                .limitMax("5000")
                .dueDatePeriod("20/12/2024")
                .duePaymentDatePeriod("31/12/2022")
                .debtClose("close")
                .build();
    }

    public static DetailPrepaidCardMWResponse withDefaultDetailPrepaidCardResponse() {
        return DetailPrepaidCardMWResponse.builder()
                .accountMaster("CMS9876543210")
                .cmsAccountNumber("ACC9876543210")
                .panNumber("1234-5678-9012-3456")
                .holderName("Juan Perez")
                .productCode("PROD001")
                .product("Tarjeta Prepaga Oro")
                .currency("068")
                .currencyDescription("BOB")
                .registrationDate("2023-01-01")
                .dueDate("2026-12-31")
                .statusCard("Activa")
                .statusAccount("Vigente")
                .placeCode("001")
                .place("Sucursal Principal")
                .insurance("S")
                .limitMin("500.00")
                .limitMax("5000.00")
                .build();
    }

    public static BlockCreditCardMWResponse withDefaultBlockCreditCardMWResponse() {
        return new BlockCreditCardMWResponse("Success", "00005");
    }

    public static BlockCreditCardMWResponse withDefaultBlockCreditCardMWResponseError() {
        return new BlockCreditCardMWResponse("Error", "10000");
    }

    public static AvailableCreditCardMWResponse withDefaultAvailableCreditCardMWResponse() {
        return new AvailableCreditCardMWResponse(new BigDecimal(100), new BigDecimal(1000));
    }

    public static PeriodCreditCardMWResponse withDefaultPeriods() {
        return PeriodCreditCardMWResponse.builder()
                .data(List.of(
                        PeriodCreditCardMWResponse.PeriodMW.builder()
                                .idPeriod(1)
                                .month("01")
                                .year("2024")
                                .initPeriod("01/01/2024")
                                .endPeriod("31/01/2024")
                                .build(),
                        PeriodCreditCardMWResponse.PeriodMW.builder()
                                .idPeriod(2)
                                .month("02")
                                .year("2024")
                                .initPeriod("01/02/2024")
                                .endPeriod("28/02/2024")
                                .build()
                ))
                .build();
    }

    public static PeriodCreditCardMWResponse withPeriodsNull() {
        return PeriodCreditCardMWResponse.builder()
                .data(null)
                .build();
    }

    public static CashAdvanceFeeMWResponse createCashAdvanceFee() {
        return CashAdvanceFeeMWResponse.builder()
                .amountCommission(BigDecimal.valueOf(100.5))
                .build();
    }

    public static LinkserCreditCardMWResponse withDefaultLinkserCreditCardMWResponse() {
        return LinkserCreditCardMWResponse.builder()
                .data(List.of(
                        LinkserCreditCardMWResponse.LinkserCreditCardMW.builder()
                                .cmsCardNumber("13-01-10-0000090001")
                                .statusCode("10")
                                .statusDescription("TARJETA ACTIVA")
                                .dueDate("202502")
                                .cardType("ORO")
                                .panNumber("1234-1234-1234-6011")
                                .branch("01")
                                .holderName("test")
                                .build(),
                        LinkserCreditCardMWResponse.LinkserCreditCardMW.builder()
                                .cmsCardNumber("13-01-10-0000090001")
                                .statusCode("10")
                                .statusDescription("TARJETA ACTIVA")
                                .dueDate(null)
                                .cardType("ORO")
                                .panNumber("1234-1234-1234-6011")
                                .branch("01")
                                .holderName("test")
                                .build(),
                        LinkserCreditCardMWResponse.LinkserCreditCardMW.builder()
                                .cmsCardNumber("13-01-10-0000090001")
                                .statusCode("10")
                                .statusDescription("TARJETA ACTIVA")
                                .dueDate("30/02/2025")
                                .cardType("ORO")
                                .panNumber("1234-1234-1234-6011")
                                .branch("01")
                                .holderName("test")
                                .build()
                ))
                .build();
    }

    public static LinkserCreditCardMWResponse withDefaultLinkserCreditCardMWResponseEmpty() {
        return LinkserCreditCardMWResponse.builder()
                .data(null)
                .build();
    }

    public static CashAdvanceMWResponse withDefaultCashAdvanceMWResponse() {
        return CashAdvanceMWResponse.builder()
                .voucher("VOUCHER123")
                .voucherDate("2024-09-12")
                .voucherTime("14:41:13")
                .amountCommission("5.00")
                .amountPaid("95.00")
                .exchangeRate("6.96")
                .swExchangeRate("0.14")
                .build();
    }

    public static CreditCardStatementsMWResponse withDefaultCreditCardStatementsMWResponse() {
        return CreditCardStatementsMWResponse.builder()
                .data(Arrays.asList(
                        CreditCardStatementsMWResponse.CreditCardStatementMW.builder()
                                .transactionDate("10/10/2023")
                                .processDate("10/10/2023")
                                .description("PAGO")
                                .currency("068")
                                .originalCurrency("068")
                                .transactionTypeDesc("CREDIT")
                                .clientName("JOSE          ")
                                .transactionStatus("POSTED")
                                .mrAmount(new BigDecimal("0.9"))
                                .mlAmount(new BigDecimal("10.1"))
                                .transactionType("PAGO")
                                .cardNumber("4099-11XX-XXXX-3055")
                                .sequenceNumber(new BigDecimal("160203"))
                                .feeNumber("1/1")
                                .paramDate("12/09/2024")
                                .build()
                ))
                .build();
    }

    public static PurchaseAuthMWResponse withDefaultPurchaseAuthMWResponse() {
        return PurchaseAuthMWResponse.builder()
                .data(Arrays.asList(
                        PurchaseAuthMWResponse.PurchaseAuthMW.builder()
                                .processDate("09/08/2024")
                                .type("Temporal")
                                .description("Habilitacion de compras por internet: Fecha Inicio: 10/08/2024 - Fecha Fin: 11/08/2024")
                                .amount("45.00")
                                .currency("840")
                                .status("PENDIENTE")
                                .origin("GMV")
                                .build()
                ))
                .build();
    }

    public static PayCreditCardMWResponse defaultPayCreditCardMWResponse() {
        return PayCreditCardMWResponse.builder()
                .status("APPROVED")
                .maeId("MAE123456789")
                .transactionNum("TRX123456789")
                .accountingEntry("ENTRY123456")
                .accountingDate("2024-08-20")
                .accountingTime("14:30:00")
                .amountDebited(BigDecimal.valueOf(100.00))
                .exchangeRateDebit(new BigDecimal("1.25"))
                .exchangeRateCredit(BigDecimal.valueOf(1.20))
                .amount(BigDecimal.valueOf(100.00))
                .currency("068")
                .fromAccountNumber("123456789012")
                .fromHolder("John Doe")
                .description("Pago de tarjeta de crédito")
                .fromCurrency("USD")
                .toCurrency("EUR")
                .tcAccountId("ACC987654321")
                .build();
    }

    public static PayCreditCardMWResponse defaultPayCreditCardMWResponsePending() {
        return PayCreditCardMWResponse.builder()
                .status("PENDING")
                .maeId("MAE123456789")
                .transactionNum("TRX123456789")
                .build();
    }

    public static AuthorizationCreditCardMWResponse withDefaultAuthorizationCreditCardMWResponse(){
        return AuthorizationCreditCardMWResponse.builder()
                .codError("TJC092")
                .desError("Success")
                .amount("100.00")
                .build();
    }

    public static AuthorizationCreditCardMWResponse withDefaultAuthorizationCreditCardMWErrorResponse(){
        return AuthorizationCreditCardMWResponse.builder()
                .codError("000")
                .desError("Error")
                .amount("100.00")
                .build();
    }

    public static FeePrepaidCardMWResponse withDefaultComissionPrepaidCardMWResponse(){
        return FeePrepaidCardMWResponse.builder()
                .transferFee(BigDecimal.valueOf(5.0))
                .insuranceAmount(BigDecimal.valueOf(100.0))
                .build();
    }
}
