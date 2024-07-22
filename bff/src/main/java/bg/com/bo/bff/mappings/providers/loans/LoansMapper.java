package bg.com.bo.bff.mappings.providers.loans;

import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoansMWResponse;
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
                        .accountLoan(mw.getLoanNumber())
                        .feePayment(Double.valueOf(mw.getFeePayment()))
                        .processDate(Util.formatDate(mw.getProcessDate()))
                        .build())
                .toList();
    }
}
