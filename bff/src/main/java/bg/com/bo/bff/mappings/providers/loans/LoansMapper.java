package bg.com.bo.bff.mappings.providers.loans;

import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentRequest;
import bg.com.bo.bff.application.dtos.request.loans.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.loans.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.providers.dtos.request.loans.mw.LoanPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.request.loans.mw.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class LoansMapper implements ILoansMapper {

    private static final String CURRENCY_BOB = "068";

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
                        .disbursementDate(UtilDate.formatDate(mw.getDisbursementDate()))
                        .amountDisbursement(Double.valueOf(mw.getAmountDisbursement()))
                        .balance(Double.valueOf(mw.getBalance()))
                        .currencyCode(mw.getCurrency())
                        .expirationDate(UtilDate.formatDate(mw.getExpirationDate()))
                        .rate(Double.valueOf(mw.getRate()))
                        .product(mw.getProduct())
                        .lastPaymentDate(UtilDate.formatDate(mw.getLastPaymentDate()))
                        .stateCode(mw.getStateCode())
                        .state(mw.getState())
                        .feePaymentDate(UtilDate.formatDate(mw.getFeePaymentDate()))
                        .feePaymentDueDate(UtilDate.formatDate(mw.getFeePaymentDueDate()))
                        .feeAmountK(Double.valueOf(mw.getFeeAmountK()))
                        .feeAmountI(Double.valueOf(mw.getFeeAmountI()))
                        .feeAmountC(Double.valueOf(mw.getFeeAmountC()))
                        .feePayment(Double.valueOf(mw.getFeePayment()))
                        .processDate(UtilDate.formatDate(mw.getProcessDate()))
                        .build())
                .toList();
    }

    @Override
    public List<LoanPaymentsResponse> convertResponse(LoanPaymentsMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> LoanPaymentsResponse.builder()
                        .date(UtilDate.formatDate(mw.getDate()))
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
                        .paymentDate(UtilDate.formatDate(mw.getPaymentDate()))
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
                        .dateInit(UtilDate.formatDate(mw.getDateInit()))
                        .dateDue(UtilDate.formatDate(mw.getDateDue()))
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
                        .dateRegister(UtilDate.formatDate(mw.getDateRegister()))
                        .clientCode(mw.getClientCode())
                        .clientName(mw.getClientName())
                        .product(mw.getProduct())
                        .branchName(mw.getBranchName())
                        .currency(mw.getCurrency())
                        .disbursementDate(UtilDate.formatDate(mw.getDisbursementDate()))
                        .disbursementAmount(Util.scaleToTwoDecimals(mw.getDisbursementAmount()))
                        .period(mw.getPeriod())
                        .teac(Util.scaleToTwoDecimals(mw.getTeac()))
                        .timeLimit(mw.getTimeLimit())
                        .rateType(mw.getRateType())
                        .nominalRate(Util.scaleToTwoDecimals(mw.getNominalRate()))
                        .baseRate(Util.scaleToTwoDecimals(mw.getBaseRate()))
                        .nameTypeRate(mw.getNameTypeRate())
                        .dateReviewRate(UtilDate.formatDate(mw.getDateReviewRate()))
                        .baseRateReviewPoint(Util.scaleToTwoDecimals(mw.getBaseRateReviewPoint()))
                        .paymentTypeInterest(mw.getPaymentTypeInterest())
                        .quantityDues(mw.getQuantityDues())
                        .build())
                .toList();
    }

    @Override
    public LoanDetailPaymentResponse convertResponse(LoanDetailPaymentMWResponse mwResponse, String currencyCode) {
        double total = 0;
        if(currencyCode.equals(CURRENCY_BOB)){
            total = Double.parseDouble(mwResponse.getAmountSecureConvertMandatory()) + Double.parseDouble(mwResponse.getAmount());
        }else {
            total = Double.parseDouble(mwResponse.getAmountSecureMandatory()) + Double.parseDouble(mwResponse.getAmount());
        }

        return LoanDetailPaymentResponse.builder()
                .correlativeId(Long.parseLong(mwResponse.getIdentifier()))
                .nroOperation(Long.parseLong(mwResponse.getLoanNumber()))
                .highDate(UtilDate.formatDate(mwResponse.getHighDate()))
                .totalFee(Integer.parseInt(mwResponse.getTotalFee()))
                .feePaid(Integer.parseInt(mwResponse.getFeePaid()))
                .expirationNextDate(UtilDate.formatDate(mwResponse.getExpirationNextDate()))
                .expirationLoanDate(UtilDate.formatDate(mwResponse.getExpirationLoanDate()))
                .interestRate(Double.parseDouble(mwResponse.getInterestRate()))
                .dateValue(UtilDate.formatDate(mwResponse.getDateValue()))
                .currentBalance(Double.parseDouble(mwResponse.getCurrentBalance()))
                .status(mwResponse.getStatus())
                .paid(!mwResponse.getErrorCode().equals("0"))
                .balanceSecure(Double.parseDouble(mwResponse.getBalanceSecure()))
                .accruedCharges(Double.parseDouble(mwResponse.getAccruedCharges()))
                .penaltyInterest(Double.parseDouble(mwResponse.getPenaltyInterest()))
                .interestCurrent(Double.parseDouble(mwResponse.getInterestCurrent()))
                .capital(Double.parseDouble(mwResponse.getCapital()))
                .form(Integer.parseInt(mwResponse.getForm()))
                .amount(Double.parseDouble(mwResponse.getAmount()))
                .secureCurrency(Integer.parseInt(mwResponse.getSecureCurrency()))
                .amountSecureMandatory(Double.parseDouble(mwResponse.getAmountSecureMandatory()))
                .amountSecureConvertMandatory(Double.parseDouble(mwResponse.getAmountSecureConvertMandatory()))
                .totalAmount(total)
                .build();
    }

    @Override
    public LoanPaymentMWRequest mapperRequest(String personId, String accountId, LoanPaymentRequest request) {
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
                        .sessionId(request.getCorrelativeId())
                        .build())
                .supplementaryData(Optional.ofNullable(request.getSupplementaryData())
                        .map(suppData -> LoanPaymentMWRequest.SupplementaryData.builder()
                                .sourceOfFunds(suppData.getSourceOfFunds())
                                .destinationOfFunds(suppData.getDestinationOfFunds())
                                .build())
                        .orElseGet(() -> LoanPaymentMWRequest.SupplementaryData.builder().build()))
                .build();
    }

    @Override
    public LoanPaymentResponse convertResponse(LoanPaymentMWResponse mwResponse) {
        return LoanPaymentResponse.builder()
                .status(mwResponse.getStatus())
                .transactionId(mwResponse.getIdReceipt())
                .maeId(mwResponse.getIdMaeTransaction())
                .accountingEntry(mwResponse.getAccountingEntry())
                .accountingDate(UtilDate.formatDate(mwResponse.getAccountingDate()))
                .accountingTime(mwResponse.getAccountingTime())
                .originAccountNumber(mwResponse.getOriginAccountNumber())
                .amount(mwResponse.getAmount())
                .currencyCode(mwResponse.getCurrency())
                .fromHolder(mwResponse.getFromHolder())
                .fromCurrencyCode(mwResponse.getFromCurrency())
                .amountDebited(mwResponse.getAmountDebited())
                .exchangeRateDebit(mwResponse.getExchangeRateDebit())
                .insuranceAmount(mwResponse.getInsuranceAmount())
                .currencyCodeInsurance(mwResponse.getCurrencyInsurance())
                .amountDebitInsurance(mwResponse.getAmountDebitInsurance())
                .exchangeRateDebitInsurance(mwResponse.getExchangeRateDebitInsurance())
                .loanId(mwResponse.getToLoanNumber())
                .loanCapital(mwResponse.getLoanCapital())
                .currentInterest(mwResponse.getCurrentInterest())
                .penaltyInterest(mwResponse.getPenaltyInterest())
                .accruedCharges(mwResponse.getAccruedCharges())
                .formsAmount(mwResponse.getFormsAmount())
                .nextDueDate(UtilDate.formatDate(mwResponse.getNextDueDate()))
                .totalInstallments(mwResponse.getTotalInstallments())
                .paidInstallments(mwResponse.getPaidInstallments())
                .amountInsuranceCurrencyLoans(mwResponse.getAmountInsuranceCurrencyLoans())
                .build();
    }

    @Override
    public Pcc01MWRequest mapperRequest(String personId, String accountId, Pcc01Request request) {
        return Pcc01MWRequest.builder()
                .currency(request.currency())
                .amount(request.amount())
                .accountId(accountId)
                .personId(personId)
                .build();
    }
}
