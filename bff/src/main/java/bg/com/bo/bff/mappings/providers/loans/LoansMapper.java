package bg.com.bo.bff.mappings.providers.loans;

import bg.com.bo.bff.application.dtos.response.loans.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.loans.mw.LoanPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class LoansMapper implements ILoansMapper {

    @Override
    public List<ListLoansResponse> convertResponse(ListLoansMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> ListLoansResponse.builder()
                        .loanId(mw.getLoanId())
                        .loanNumber(mw.getLoanNumber())
                        .clientId(mw.getClientCode())
                        .customerName(mw.getCustomerName())
                        .disbursementDate(Util.formatDate(mw.getDisbursementDate()))
                        .amountDisbursement(Double.valueOf(mw.getAmountDisbursement()))
                        .balance(Double.valueOf(mw.getBalance()))
                        .currencyCode(mw.getCurrency())
                        .expirationDate(Util.formatDate(mw.getExpirationDate()))
                        .rate(Double.valueOf(mw.getRate()))
                        .product(mw.getProduct())
                        .lastPaymentDate(Util.formatDate(mw.getLastPaymentDate()))
                        .stateCode(mw.getStateCode())
                        .state(mw.getState())
                        .feePaymentDate(Util.formatDate(mw.getFeePaymentDate()))
                        .feePaymentDueDate(Util.formatDate(mw.getFeePaymentDueDate()))
                        .feeAmountK(Double.valueOf(mw.getFeeAmountK()))
                        .feeAmountI(Double.valueOf(mw.getFeeAmountI()))
                        .feeAmountC(Double.valueOf(mw.getFeeAmountC()))
                        .feePayment(Double.valueOf(mw.getFeePayment()))
                        .processDate(Util.formatDate(mw.getProcessDate()))
                        .build())
                .toList();
    }

    @Override
    public List<LoanPaymentsResponse> convertResponse(LoanPaymentsMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> LoanPaymentsResponse.builder()
                        .date(Util.formatDate(mw.getDate()))
                        .accountEntry(mw.getAccountEntry())
                        .advancedCapital(Util.convertStringToBigDecimal(mw.getAdvancedCapital()))
                        .originalCapital(Util.convertStringToBigDecimal(mw.getOriginalCapital()))
                        .capitalPaid(Util.convertStringToBigDecimal(mw.getCapitalPaid()))
                        .expenses(Util.convertStringToBigDecimal(mw.getExpenses()))
                        .interestAmountPaid(Util.convertStringToBigDecimal(mw.getInteresAmountPaid()))
                        .payLateFees(Util.convertStringToBigDecimal(mw.getPayLateFees()))
                        .balance(Util.convertStringToBigDecimal(mw.getBalance()))
                        .typeMovement(mw.getTypeMovement())
                        .totalInstallment(Util.convertStringToBigDecimal(mw.getTotalInstallment()))
                        .branch(mw.getBranch())
                        .build())
                .toList();
    }

    @Override
    public List<LoanInsurancePaymentsResponse> convertResponse(LoanInsurancePaymentsMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> LoanInsurancePaymentsResponse.builder()
                        .warrantyNumber(mw.getWarrantyNumber())
                        .description(mw.getDescription())
                        .currencyCode(mw.getCurrency())
                        .currencyDescription(mw.getCurrencyDescription())
                        .paymentNumber(mw.getPaymentNumber())
                        .paymentDate(Util.formatDate(mw.getPaymentDate()))
                        .amount(Util.scaleToTwoDecimals(mw.getAmount()))
                        .index(mw.getRecord())
                        .build())
                .toList();
    }

    @Override
    public List<LoanPlanResponse> convertResponse(LoanPlanMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> LoanPlanResponse.builder()
                        .identifier(mw.getIdentifier())
                        .loanId(mw.getLoanId())
                        .loanNumber(mw.getLoanNumber())
                        .quotaNumber(mw.getQuotaNumber())
                        .dateInit(Util.formatDate(mw.getDateInit()))
                        .dateDue(Util.formatDate(mw.getDateDue()))
                        .quotaType(mw.getQuotaType())
                        .capital(Util.scaleToTwoDecimals(mw.getCapital()))
                        .interest(Util.scaleToTwoDecimals(mw.getInterest()))
                        .charge1(Util.scaleToTwoDecimals(mw.getCharge1()))
                        .charge2(Util.scaleToTwoDecimals(mw.getCharge2()))
                        .charge3(Util.scaleToTwoDecimals(mw.getCharge3()))
                        .charge4(Util.scaleToTwoDecimals(mw.getCharge4()))
                        .charge5(Util.scaleToTwoDecimals(mw.getCharge5()))
                        .charge6(Util.scaleToTwoDecimals(mw.getCharge6()))
                        .residual(Util.scaleToTwoDecimals(mw.getRemanent()))
                        .dateRegister(Util.formatDate(mw.getDateRegister()))
                        .clientCode(mw.getClientCode())
                        .clientName(mw.getClientName())
                        .product(mw.getProduct())
                        .branchName(mw.getBranchName())
                        .currency(mw.getCurrency())
                        .disbursementDate(Util.formatDate(mw.getDisbursementDate()))
                        .disbursementAmount(Util.scaleToTwoDecimals(mw.getDisbursementAmount()))
                        .period(mw.getPeriod())
                        .teac(Util.scaleToTwoDecimals(mw.getTeac()))
                        .timeLimit(mw.getTimeLimit())
                        .rateType(mw.getRateType())
                        .nominalRate(Util.scaleToTwoDecimals(mw.getNominalRate()))
                        .baseRate(Util.scaleToTwoDecimals(mw.getBaseRate()))
                        .nameTypeRate(mw.getNameTypeRate())
                        .dateReviewRate(Util.formatDate(mw.getDateReviewRate()))
                        .baseRateReviewPoint(Util.scaleToTwoDecimals(mw.getBaseRateReviewPoint()))
                        .paymentTypeInterest(mw.getPaymentTypeInterest())
                        .quantityDues(mw.getQuantityDues())
                        .build())
                .toList();
    }

    @Override
    public LoanDetailPaymentResponse convertResponse(LoanDetailPaymentMWResponse mwResponse) {
        return LoanDetailPaymentResponse.builder()
                .correlativeId(Long.parseLong(mwResponse.getIdentifier()))
                .nroOperation(Long.parseLong(mwResponse.getLoanNumber()))
                .highDate(Util.formatDate(mwResponse.getHighDate()))
                .totalFee(Integer.parseInt(mwResponse.getTotalFee()))
                .feePaid(Integer.parseInt(mwResponse.getFeePaid()))
                .expirationNextDate(Util.formatDate(mwResponse.getExpirationNextDate()))
                .expirationLoanDate(Util.formatDate(mwResponse.getExpirationLoanDate()))
                .interestRate(Double.parseDouble(mwResponse.getInterestRate()))
                .dateValue(Util.formatDate(mwResponse.getDateValue()))
                .currentBalance(Double.parseDouble(mwResponse.getCurrentBalance()))
                .status(mwResponse.getStatus())
                .balanceSecure(Double.parseDouble(mwResponse.getBalanceSecure()))
                .accruedCharges(Double.parseDouble(mwResponse.getAccruedCharges()))
                .interestCurrent(Double.parseDouble(mwResponse.getInterestCurrent()))
                .capital(Double.parseDouble(mwResponse.getCapital()))
                .form(Integer.parseInt(mwResponse.getForm()))
                .amount(Double.parseDouble(mwResponse.getAmount()))
                .secureCurrency(Integer.parseInt(mwResponse.getSecureCurrency()))
                .amountSecureMandatory(Double.parseDouble(mwResponse.getAmountSecureMandatory()))
                .build();
    }

    @Override
    public LoanPaymentMWRequest mapperRequest(String personId, String accountId, String correlativeId) {
        return LoanPaymentMWRequest.builder()
                .ownerAccountRequest(LoanPaymentMWRequest.OwnAccount.builder()
                        .schemaName("PersonId")
                        .personId(personId)
                        .companyId("0")
                        .build())
                .debtorAccountRequest(LoanPaymentMWRequest.DebtorAccount.builder()
                        .schemaName("AccountId")
                        .identification(accountId)
                        .build())
                .creditorAccountRequest(LoanPaymentMWRequest.CreditorAccount.builder()
                        .schemaName("SessionId")
                        .sessionId(correlativeId)
                        .build())
                .build();
    }

    @Override
    public LoanPaymentResponse convertResponse(LoanPaymentMWResponse mwResponse) {
        return LoanPaymentResponse.builder()
                .status(mwResponse.getStatus())
                .transactionId(mwResponse.getNroTransaction())
                .maeId(mwResponse.getMaeId())
                .accountingEntry(mwResponse.getLoanReceiptDetail().getAccountingEntry())
                .accountingDate(mwResponse.getLoanReceiptDetail().getAccountingDate())
                .accountingTime(mwResponse.getLoanReceiptDetail().getAccountingTime())
                .originAccountNumber(mwResponse.getLoanReceiptDetail().getOriginAccountNumber())
                .amount(mwResponse.getLoanReceiptDetail().getAmount())
                .currencyCode(mwResponse.getLoanReceiptDetail().getCurrency())
                .fromHolder(mwResponse.getLoanReceiptDetail().getFromHolder())
                .fromCurrencyCode(mwResponse.getLoanReceiptDetail().getFromCurrency())
                .amountDebited(mwResponse.getLoanReceiptDetail().getAmountDebited())
                .exchangeRateDebit(mwResponse.getLoanReceiptDetail().getExchangeRateDebit())
                .insuranceAmount(mwResponse.getLoanReceiptDetail().getInsuranceAmount())
                .currencyCodeInsurance(mwResponse.getLoanReceiptDetail().getCurrencyInsurance())
                .amountDebitInsurance(mwResponse.getLoanReceiptDetail().getAmountDebitInsurance())
                .exchangeRateDebit(mwResponse.getLoanReceiptDetail().getExchangeRateDebit())
                .loanId(mwResponse.getLoanReceiptDetail().getToLoanNumber())
                .loanCapital(mwResponse.getLoanReceiptDetail().getLoanCapital())
                .currentInterest(mwResponse.getLoanReceiptDetail().getCurrentInterest())
                .penaltyInterest(mwResponse.getLoanReceiptDetail().getPenaltyInterest())
                .accruedCharges(mwResponse.getLoanReceiptDetail().getAccruedCharges())
                .formsAmount(mwResponse.getLoanReceiptDetail().getFormsAmount())
                .nextDueDate(mwResponse.getLoanReceiptDetail().getNextDueDate())
                .totalInstallments(mwResponse.getLoanReceiptDetail().getTotalInstallments())
                .paidInstallments(mwResponse.getLoanReceiptDetail().getPaidInstallments())
                .build();
    }
}
