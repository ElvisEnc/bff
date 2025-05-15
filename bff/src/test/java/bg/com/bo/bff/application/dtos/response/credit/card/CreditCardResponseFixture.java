package bg.com.bo.bff.application.dtos.response.credit.card;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreditCardResponseFixture {
    public static ListCreditCardResponse withDefaultListCreditCardResponse() {
        return ListCreditCardResponse.builder()
                .creditCards(Collections.singletonList(
                        createCreditCardResponse()
                ))
                .prepaidCards(Collections.singletonList(
                        createPrepaidCardResponse()
                ))
                .build();
    }

    public static ListCreditCardResponse.CreditCardResponse createCreditCardResponse() {
        return ListCreditCardResponse.CreditCardResponse.builder()
                .cardId("123")
                .product("Visa")
                .cardNumber("**** **** **** 1111")
                .cmsAccount("CMS12345")
                .holderName("John Doe")
                .currency("068")
                .dueDate("31/12/2024")
                .limitAmount("5000")
                .typeCard("Credit")
                .clientCode("C12345")
                .build();
    }

    public static ListCreditCardResponse.PrepaidCardResponse createPrepaidCardResponse() {
        return ListCreditCardResponse.PrepaidCardResponse.builder()
                .cardId("123")
                .product("Mastercard")
                .cardNumber("**** **** **** 0004")
                .cmsAccount("CMS54321")
                .holderName("Jane Doe")
                .currency("840")
                .dueDate("01/01/2025")
                .typeCard("Prepaid")
                .clientCode("C54321")
                .status("Active")
                .solicitudeDate("15/01/2023")
                .typeCardCode("P123")
                .build();
    }

    public static ListCreditCardResponse withDefaultListCreditCardResponseEmpty() {
        return ListCreditCardResponse.builder()
                .creditCards(new ArrayList<>())
                .prepaidCards(new ArrayList<>())
                .build();
    }

    public static DetailCreditCardResponse withDefaultDetailCreditCardResponse() {
        return DetailCreditCardResponse.builder()
                .cardId("123")
                .cmsAccount("987654321")
                .cardNumber("**** **** **** 3456")
                .panNumber("1234-5678-9012-3456")
                .holderName("John Doe")
                .placeCode("001")
                .place("Main Branch")
                .dueDate("31/12/2024")
                .registrationDate("01/01/2022")
                .cardStatus("Active")
                .accountStatus("Active")
                .insurance(true)
                .currency("USD")
                .amountAvailable(BigDecimal.valueOf(2000))
                .limitMin("100")
                .limitMax("5000")
                .debtClose("close")
                .amountLimit("5000")
                .duePaymentDatePeriod("31/12/2022")
                .dueDatePeriod("20/12/2024")
                .paymentAmountMinimum("10")
                .amountPaymentCurrent(BigDecimal.valueOf(350))
                .build();
    }

    public static DetailPrepaidCardResponse withDefaultDetailPrepaidCardResponse() {
        return DetailPrepaidCardResponse.builder()
                .cardId("CMS9876543210")
                .cmsAccount("ACC9876543210")
                .cardNumber("**** **** **** 3456")
                .holderName("Juan Perez")
                .productCode("PROD001")
                .product("Tarjeta Prepaga Oro")
                .currency("068")
                .availableAmount(BigDecimal.valueOf(1800.9))
                .lastRechargeDate("01/01/2025")
                .registrationDate("01/01/2023")
                .dueDate("31/12/2026")
                .cardStatus("Activa")
                .accountStatus("Vigente")
                .placeCode("001")
                .place("Sucursal Principal")
                .insurance("S")
                .limitMin("500.00")
                .limitMax("5000.00")
                .build();
    }

    public static AvailableCreditCardResponse withDefaultAvailableCreditCardResponse() {
        return new AvailableCreditCardResponse(new BigDecimal(100), new BigDecimal(1000));
    }

    public static List<PeriodCreditCardResponse> withDefaultPeriods() {
        return List.of(
                PeriodCreditCardResponse.builder()
                        .periodCode(1)
                        .month("01")
                        .year("2024")
                        .init("01/01/2024")
                        .end("31/01/2024")
                        .build(),
                PeriodCreditCardResponse.builder()
                        .periodCode(2)
                        .month("02")
                        .year("2024")
                        .init("01/02/2024")
                        .end("28/02/2024")
                        .build()
        );
    }

    public static CashAdvanceFeeResponse createCashAdvanceFeeResponse() {
        return CashAdvanceFeeResponse.builder()
                .fee(BigDecimal.valueOf(100.5))
                .build();
    }

    public static List<LinkserCreditCardResponse> withDefaultLinkserCreditCardResponse() {
        return List.of(
                LinkserCreditCardResponse.builder()
                        .cmsCard("13-01-10-0000090001")
                        .cardNumber("**** **** **** 6011")
                        .holderName("test")
                        .dueDate("02/2025")
                        .branch("01")
                        .cardType("ORO")
                        .statusCode("10")
                        .statusDescription("TARJETA ACTIVA")
                        .build(),
                LinkserCreditCardResponse.builder()
                        .cmsCard("13-01-10-0000090001")
                        .cardNumber("**** **** **** 6011")
                        .holderName("test")
                        .dueDate(null)
                        .branch("01")
                        .cardType("ORO")
                        .statusCode("10")
                        .statusDescription("TARJETA ACTIVA")
                        .build(),
                LinkserCreditCardResponse.builder()
                        .cmsCard("13-01-10-0000090001")
                        .cardNumber("**** **** **** 6011")
                        .holderName("test")
                        .dueDate("30/02/2025")
                        .branch("01")
                        .cardType("ORO")
                        .statusCode("10")
                        .statusDescription("TARJETA ACTIVA")
                        .build()
        );
    }

    public static CashAdvanceResponse withDefaultCashAdvanceResponse() {
        return CashAdvanceResponse.builder()
                .voucher("VOUCHER123")
                .voucherDate("12/09/2024")
                .voucherTime("14:41:13")
                .amountCommission("5.00")
                .amountPaid("95.00")
                .exchangeRate("6.96")
                .swExchangeRate("0.14")
                .build();
    }

    public static CreditCardStatementsResponse withDefaultCreditCardStatementsResponse() {
        return CreditCardStatementsResponse.builder()
                .transactionDate("10/10/2023")
                .processDate("10/10/2023")
                .description("PAGO")
                .currency("068")
                .transactionTypeDesc("CREDIT")
                .clientName("JOSE")
                .transactionStatus("POSTED")
                .mrAmount(BigDecimal.valueOf(0.90).setScale(2, RoundingMode.DOWN))
                .mlAmount(BigDecimal.valueOf(10.10).setScale(2, RoundingMode.DOWN))
                .transactionType("PAGO")
                .cardNumber("**** **** **** 3055")
                .sequenceNumber(new BigDecimal("160203"))
                .feeNumber("1/1")
                .paramDate("12/09/2024")
                .build();
    }

    public static CreditCardStatementsResponse withDefaultsRejected() {
        return CreditCardStatementsResponse.builder()
                .transactionDate("10/10/2023")
                .processDate("10/10/2023")
                .description("PAGO")
                .currency("068")
                .transactionTypeDesc("CREDIT")
                .clientName("JOSE")
                .transactionStatus("NOPOSTED")
                .mrAmount(BigDecimal.valueOf(0.90).setScale(2, RoundingMode.DOWN))
                .mlAmount(BigDecimal.valueOf(10.10).setScale(2, RoundingMode.DOWN))
                .transactionType("PAGO")
                .cardNumber("**** **** **** 3055")
                .sequenceNumber(new BigDecimal("160203"))
                .feeNumber("1/1")
                .paramDate("12/09/2024")
                .build();
    }

    public static CreditCardStatementsResponse withDefaultCreditCardStatementsResponse2() {
        return CreditCardStatementsResponse.builder()
                .transactionDate("2023-10-10")
                .processDate("2023-10-10")
                .description("PAGO")
                .currency("068")
                .transactionTypeDesc("CREDIT")
                .clientName("JOSE")
                .transactionStatus("POSTED")
                .mrAmount(BigDecimal.valueOf(100.50).setScale(2, RoundingMode.DOWN))
                .mlAmount(BigDecimal.valueOf(0.0).setScale(2, RoundingMode.DOWN))
                .transactionType("PAGO")
                .cardNumber("**** **** **** 3055")
                .sequenceNumber(new BigDecimal("160203"))
                .feeNumber("1/1")
                .paramDate("2024-09-12")
                .build();
    }

    public static CreditCardStatementsResponse withDefaultCreditCardStatementsResponse3() {
        return CreditCardStatementsResponse.builder()
                .transactionDate("2023-10-10")
                .processDate("2023-10-10")
                .description("PAGO")
                .currency("068")
                .transactionTypeDesc("CREDIT")
                .clientName("JOSE")
                .transactionStatus("POSTED")
                .mrAmount(BigDecimal.valueOf(100.50).setScale(2, RoundingMode.DOWN))
                .mlAmount(null)
                .transactionType("PAGO")
                .cardNumber("**** **** **** 3055")
                .sequenceNumber(new BigDecimal("160203"))
                .feeNumber("1/1")
                .paramDate("2024-09-12")
                .build();
    }

    public static PurchaseAuthResponse withDefaultPurchaseAuthResponse() {
        return PurchaseAuthResponse.builder()
                .processDate("09/08/2024")
                .type("Temporal")
                .amount("45.00")
                .currency("840")
                .status("PENDIENTE")
                .origin("GMV")
                .initDate("01/01/2025")
                .endDate("01/01/2025")
                .requestType("Internet")
                .build();
    }

    public static PurchaseAuthResponse withDefaultPurchaseAuthResponseSpecial() {
        return PurchaseAuthResponse.builder()
                .processDate("09/08/2024")
                .type("Temporal")
                .amount("45.00")
                .currency("840")
                .status("PENDIENTE")
                .origin("GMV")
                .initDate("01/01/2025")
                .endDate("01/01/2025")
                .requestType("Liberacion Parametros")
                .build();
    }

    public static PayCreditCardResponse defaultPayCreditCardResponse() {
        return PayCreditCardResponse.builder()
                .status("APPROVED")
                .transactionId("TRX123456789")
                .maeId("MAE123456789")
                .accountingEntry("ENTRY123456")
                .accountingDate("20/08/2024")
                .accountingTime("14:30:00")
                .amountDebited(new BigDecimal("100.00"))
                .exchangeRateDebit(new BigDecimal("1.25"))
                .exchangeRateCredit(BigDecimal.valueOf(1.20))
                .amount(new BigDecimal("100.00"))
                .currency("068")
                .fromAccountNumber("123456789012")
                .fromHolder("John Doe")
                .description("Pago de tarjeta de crédito")
                .fromCurrency("USD")
                .toCurrency("EUR")
                .tcAccountId("ACC987654321")
                .build();
    }

    public static FeePrepaidCardResponse withDefaultComissionPrepaidCardResponse() {
        return FeePrepaidCardResponse.builder()
                .amount(new BigDecimal("100.0"))
                .fee(new BigDecimal("5.0"))
                .build();
    }
}
