package bg.com.bo.bff.application.dtos.request.credit.card;

import bg.com.bo.bff.application.dtos.request.commons.PeriodRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
import bg.com.bo.bff.application.dtos.request.transfer.AmountTransfer;
import bg.com.bo.bff.application.dtos.request.transfer.DataTransfer;
import bg.com.bo.bff.application.dtos.request.transfer.TargetAccount;

import java.math.BigDecimal;

public class CreditCardRequestFixture {
    public static BlockCreditCardRequest withDefaultBlockCreditCardRequest() {
        return BlockCreditCardRequest.builder()
                .cmsCard("13-01-10-0201360001")
                .type("2")
                .typeCard("TC")
                .build();
    }

    public static BlockCreditCardRequest withDefaultBlockCreditCardRequestUnlock() {
        return BlockCreditCardRequest.builder()
                .cmsCard("13-01-10-0201360001")
                .type("0")
                .build();
    }

    public static CashAdvanceRequest withDefaultCashAdvanceRequest() {
        return CashAdvanceRequest.builder()
                .cmsAccount("13-45-10-123456")
                .cmsCard("13-45-10-1234567890")
                .panNumber("4099-11XX-XXXX-2018")
                .dueDate("30/11/2029")
                .accountId("123456")
                .amount(new BigDecimal("100.00"))
                .description("Cash advance for travel")
                .build();
    }

    public static CreditCardStatementRequest withDefaultCreditCardStatementRequest() {
        return CreditCardStatementRequest.builder()
                .cmsCard("13-07-10-000005")
                .filters(CreditCardStatementRequest.CreditCardFilter.builder()
                        .pagination(withDefaultPaginationRequest())
                        .date(withDefaultPeriodRequest())
                        .order(withDefaultOrderRequest())
                        .build())
                .refreshData(true)
                .build();
    }

    private static PaginationRequest withDefaultPaginationRequest() {
        return PaginationRequest.builder()
                .page(1)
                .pageSize(10)
                .build();
    }

    private static PeriodRequest withDefaultPeriodRequest() {
        return PeriodRequest.builder()
                .start("2023-10-10")
                .end("2024-01-31")
                .build();
    }

    private static OrderRequest withDefaultOrderRequest() {
        return OrderRequest.builder()
                .field("DATE")
                .desc(false)
                .build();
    }

    public static CreditCardStatementRequest withDefaultCreditCardStatementRequestNull() {
        return CreditCardStatementRequest.builder()
                .cmsCard("13-07-10-0000000005")
                .filters(CreditCardStatementRequest.CreditCardFilter.builder()
                        .pagination(null)
                        .date(withDefaultPeriodRequest())
                        .order(null)
                        .build())
                .refreshData(true)
                .build();
    }

    public static CreditCardStatementRequest withDefaultCreditCardStatementRequestAmount() {
        return CreditCardStatementRequest.builder()
                .cmsCard("13-07-10-0000000005")
                .filters(CreditCardStatementRequest.CreditCardFilter.builder()
                        .pagination(null)
                        .date(withDefaultPeriodRequest())
                        .order(withDefaultOrderRequestAmount())
                        .build())
                .refreshData(true)
                .build();
    }

    private static OrderRequest withDefaultOrderRequestAmount() {
        return OrderRequest.builder()
                .field("AMOUNT")
                .desc(true)
                .build();
    }

    public static PayCreditCardRequest defaultPayCreditCardRequest() {
        return PayCreditCardRequest.builder()
                .targetAccount(TargetAccount.builder()
                        .id("123")
                        .build())
                .amount(AmountTransfer.builder()
                        .currency("068")
                        .amount(new BigDecimal("100.00"))
                        .build())
                .transactionType("TC")
                .data(DataTransfer.builder()
                        .description("Pago de TC")
                        .sourceOfFunds("Cuenta de ahorros ahorros ahorros")
                        .destinationOfFunds("Pago de tarjeta de crédito para mas credito")
                        .build())
                .build();
    }

    public static AuthorizationCreditCardRequest withDefaultAuthorizationCreditCardRequest() {
        return AuthorizationCreditCardRequest.builder()
                .cmsAccount("13-45-10-123456")
                .cmsCard("13-07-10-0000000005")
                .amount(new BigDecimal("100.00"))
                .period(PeriodRequest.builder()
                        .start("2024-10-10")
                        .end("2024-01-31")
                        .build())
                .type("H")
                .requestType("I")
                .build();
    }

    public static FeePrepaidCardRequest withDefaultComissionPrepaidCardRequest() {
        return FeePrepaidCardRequest.builder()
                .cmsAccount("13-45-10-129")
                .amount(new BigDecimal("100.00"))
                .build();
    }
}
