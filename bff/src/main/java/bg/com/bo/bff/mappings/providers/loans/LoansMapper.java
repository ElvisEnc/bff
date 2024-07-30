package bg.com.bo.bff.mappings.providers.loans;

import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanInsurancePaymentsResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanPaymentsResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanPlanResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.loans.mw.LoanInsurancePaymentsMWResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.LoanPaymentsMWResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoansMWResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.LoanPlanMWResponse;
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
                        .customerName(mw.getCustomerName())
                        .disbursementDate(Util.formatDate(mw.getDisbursementDate()))
                        .amountDisbursement(Double.valueOf(mw.getAmountDisbursement()))
                        .balance(Double.valueOf(mw.getBalance()))
                        .currency(mw.getCurrency())
                        .expirationDate(Util.formatDate(mw.getExpirationDate()))
                        .rate(Double.valueOf(mw.getRate()))
                        .product(mw.getProduct())
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
                        .advancedCapital(mw.getAdvancedCapital())
                        .originalCapital(mw.getOriginalCapital())
                        .capitalPaid(mw.getCapitalPaid())
                        .expenses(mw.getExpenses())
                        .interestAmountPaid(mw.getInteresAmountPaid())
                        .payLateFees(mw.getPayLateFees())
                        .balance(mw.getBalance())
                        .typeMovement(mw.getTypeMovement())
                        .totalInstallment(mw.getTotalInstallment())
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
                        .amount(mw.getAmount())
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
                        .capital(mw.getCapital())
                        .interest(mw.getInterest())
                        .charge1(mw.getCharge1())
                        .charge2(mw.getCharge2())
                        .charge3(mw.getCharge3())
                        .charge4(mw.getCharge4())
                        .charge5(mw.getCharge5())
                        .charge6(mw.getCharge6())
                        .residual(mw.getRemanent())
                        .dateRegister(Util.formatDate(mw.getDateRegister()))
                        .clientCode(mw.getClientCode())
                        .clientName(mw.getClientName())
                        .product(mw.getProduct())
                        .branchName(mw.getBranchName())
                        .currency(mw.getCurrency())
                        .disbursementDate(Util.formatDate(mw.getDisbursementDate()))
                        .disbursementAmount(mw.getDisbursementAmount())
                        .period(mw.getPeriod())
                        .teac(mw.getTeac())
                        .timeLimit(mw.getTimeLimit())
                        .rateType(mw.getRateType())
                        .nominalRate(mw.getNominalRate())
                        .baseRate(mw.getBaseRate())
                        .nameTypeRate(mw.getNameTypeRate())
                        .dateReviewRate(Util.formatDate(mw.getDateReviewRate()))
                        .baseRateReviewPoint(mw.getBaseRateReviewPoint())
                        .paymentTypeInterest(mw.getPaymentTypeInterest())
                        .quantityDues(mw.getQuantityDue())
                        .build())
                .toList();
    }
}
