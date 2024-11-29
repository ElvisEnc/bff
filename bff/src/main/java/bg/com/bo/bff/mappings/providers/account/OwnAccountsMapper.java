package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.request.own.account.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.application.dtos.response.own.account.OwnAccountsResponse;
import bg.com.bo.bff.application.dtos.response.own.account.TransactionLimitsResponse;
import bg.com.bo.bff.commons.enums.account.statement.AccountStatementType;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.UpdateTransactionLimitMWRequest;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.OwnAccountsListMWResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.TransactionLimitsMWResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class OwnAccountsMapper implements IOwnAccountsMapper {
    @Override
    public List<OwnAccountsResponse> convertResponse(OwnAccountsListMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> OwnAccountsResponse.builder()
                        .accountId(mw.getAccountId())
                        .accountNumber(mw.getAccountNumber())
                        .clientName(mw.getClientName())
                        .clientCode(mw.getClientCode())
                        .accountHolderCode(mw.getAccountHolderCode())
                        .currencyCode(mw.getCurrencyCode())
                        .currencyDescription(mw.getCurrencyDescription())
                        .productDescription(mw.getProductDescription())
                        .accountManagementCode(mw.getAccountManagementCode())
                        .accountType(mw.getAccountType())
                        .availiableBalance(Util.scaleToTwoDecimals(mw.getAvailiableBalance()))
                        .accountManagementDescription(mw.getAccountManagementDescription())
                        .openingDate(UtilDate.formatDate(mw.getOpeningDate()))
                        .dateOfLastMovement(UtilDate.formatDate(mw.getDateOfLastMovement()))
                        .totalBalance(Util.scaleToTwoDecimals(mw.getTotalBalance()))
                        .pledgeFounds(Util.scaleToTwoDecimals(mw.getPledgeFounds()))
                        .pendingDeposits(Util.scaleToTwoDecimals(mw.getPendingDeposits()))
                        .statusCode(mw.getStatusCode())
                        .statusDescription(mw.getStatusDescription())
                        .branchCode(mw.getBranchCode())
                        .branchDescription(mw.getBranchDescription())
                        .departamentCode(mw.getDepartamentCode())
                        .departamentDescription(mw.getDepartamentDescription())
                        .accountUsage(mw.getAccountUsage())
                        .accountUsageDescription(mw.getAccountUsageDescription())
                        .build())
                .toList();
    }

    @Override
    public UpdateTransactionLimitMWRequest mapperRequest(UpdateTransactionLimitRequest request) {
        return new UpdateTransactionLimitMWRequest(request.amountLimit(), request.countLimit());
    }

    @Override
    public TransactionLimitsResponse convertResponse(TransactionLimitsMWResponse mwResponse) {
        return TransactionLimitsResponse.builder()
                .countLimit(Util.convertStringToInteger(mwResponse.getTransactionPermitDay()))
                .countLimitRegistered(Util.convertStringToInteger(mwResponse.getTransactionsRegisteredInDay()))
                .amountLimit(Util.convertStringToInteger(mwResponse.getAvailableTransaction()))
                .amountLimitGroup(Util.convertStringToInteger(mwResponse.getAvailableTransactionGroup()))
                .currencyCode(mwResponse.getCurrencyCod())
                .type(mwResponse.getType())
                .build();
    }

    @Override
    public AccountStatementsMWRequest mapperRequest(String accountId, String personId, String init, String total, AccountStatementsRequest request) {
        return AccountStatementsMWRequest.builder()
                .personId(personId)
                .companyId(personId)
                .accountId(accountId)
                .startDate(request.getFilters().getDate().getStart())
                .endDate(request.getFilters().getDate().getEnd())
                .initCount(init)
                .totalCount(total)
                .build();
    }

    @Override
    public List<AccountStatementsResponse> convertResponse(AccountStatementsMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("ACEP", 1);
        hashMap.put("ENPROC", 2);
        hashMap.put("RECH", 3);
        return mwResponse.getData().stream()
                .map(mw -> AccountStatementsResponse.builder()
                        .status(String.valueOf(hashMap.get(mw.getStatus())))
                        .movementType(Objects.equals(mw.getMoveType(), "D") ? AccountStatementType.DEBITO.getCode() : AccountStatementType.CREDITO.getCode())
                        .amount(Util.scaleToTwoDecimals(mw.getAmount()))
                        .currencyCode(mw.getCurrencyCod())
                        .description(mw.getDescription())
                        .balance(Util.scaleToTwoDecimals(mw.getCurrentBalance()))
                        .movementDate(UtilDate.formatDate(mw.getProcessDate()))
                        .movementTime(mw.getAccountingTime())
                        .channel(mw.getBranchOffice())
                        .seatNumber(String.valueOf(mw.getSeatNumber()))
                        .build())
                .toList();
    }
}
