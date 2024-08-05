package bg.com.bo.bff.providers.dtos.request.own.account.mw;


public record UpdateTransactionLimitMWRequest(
        String availableTransaction,
        String transactionPermitDay
) {
}

