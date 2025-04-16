package bg.com.bo.bff.mappings.providers.card;

import bg.com.bo.bff.application.dtos.request.credit.card.*;
import bg.com.bo.bff.application.dtos.response.credit.card.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.providers.dtos.request.credit.card.*;
import bg.com.bo.bff.providers.dtos.request.credit.card.CashAdvanceMWRequest;
import bg.com.bo.bff.providers.dtos.response.credit.card.mw.*;
import bg.com.bo.bff.providers.models.enums.middleware.credit.card.CreditCardConstans;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class CreditCardMapper implements ICreditCardMapper {

    private static final String REQUEST_TYPE_INTERNET = "I";
    private static final String TYPE_INTERNET = "Internet";
    private static final String RESPONSE_TEXT_ACTIVE = "ACTIVA";
    private static final String RESPONSE_TEXT_FORMATED = "ACTIVADA";
    private static final String RESPONSE_TEXT_BLOCKED = "BLOQUEADA";
    private static final String TYPE_SPECIAL = "Liberacion Parametros";
    private static final String TRANSACTIONS_STATUS_POSTED = "POSTED";
    private static final String TRANSACTIONS_STATUS_POSTED_FORMATTED = "PROCESADA";
    private static final String TRANSACTIONS_STATUS_NO_POSTED_FORMATTED = "RECHAZADA";

    @Override
    public ListCreditCardResponse convertResponse(ListCreditCardMWResponse mwResponse) {
        if (mwResponse == null)
            return ListCreditCardResponse.builder()
                    .creditCards(new ArrayList<>())
                    .prepaidCards(new ArrayList<>())
                    .build();

        List<ListCreditCardResponse.CreditCardResponse> creditCardResponses = Optional.ofNullable(mwResponse.getCreditCards())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(this::mapCreditCard)
                .toList();

        List<ListCreditCardResponse.PrepaidCardResponse> prepaidCardResponses = Optional.ofNullable(mwResponse.getPrepaidCards())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(this::mapPrepaidCard)
                .toList();

        return ListCreditCardResponse.builder()
                .creditCards(creditCardResponses)
                .prepaidCards(prepaidCardResponses)
                .build();
    }

    private ListCreditCardResponse.CreditCardResponse mapCreditCard(ListCreditCardMWResponse.CreditCardMWResponse ccMW) {
        String[] words = ccMW.getProduct().split(" ");
        return ListCreditCardResponse.CreditCardResponse.builder()
                .cardId(ccMW.getAccountMaster())
                .product(words[words.length - 1])
                .cardNumber(Util.obfuscateCardNumber(ccMW.getPanNumber()))
                .cmsAccount(ccMW.getCmsAccountNumber())
                .holderName(ccMW.getHolderName())
                .currency(ccMW.getCurrency())
                .dueDate(UtilDate.formatDate(ccMW.getDueDate()))
                .limitAmount(ccMW.getLimitAmount())
                .typeCard(ccMW.getTypeCard())
                .clientCode(ccMW.getClientCode())
                .build();
    }

    private ListCreditCardResponse.PrepaidCardResponse mapPrepaidCard(ListCreditCardMWResponse.PrepaidCardMWResponse pcMW) {
        String[] words = pcMW.getProduct().split(" ");
        return ListCreditCardResponse.PrepaidCardResponse.builder()
                .cardId(pcMW.getAccountMaster())
                .product(words[words.length - 1])
                .cardNumber(Util.obfuscateCardNumber(pcMW.getPanNumber()))
                .cmsAccount(pcMW.getCmsAccountNumber())
                .holderName(pcMW.getHolderName())
                .currency(pcMW.getCurrency())
                .dueDate(UtilDate.formatDate(pcMW.getDueDate()))
                .typeCard(pcMW.getTypeCard())
                .clientCode(pcMW.getClientCode())
                .status(pcMW.getStatus())
                .solicitudeDate(UtilDate.formatDate(pcMW.getSolicitudeDate()))
                .typeCardCode(pcMW.getTypeCardCode())
                .build();
    }

    @Override
    public DetailCreditCardResponse convertDetails(DetailsCreditCardMWResponse mwResponse) {
        return DetailCreditCardResponse.builder()
                .cardId(mwResponse.getAccountMaster())
                .cmsAccount(mwResponse.getCmsAccountNumber())
                .cardNumber(Util.obfuscateCardNumber(mwResponse.getPanNumber()))
                .holderName(mwResponse.getHolderName())
                .placeCode(mwResponse.getPlaceCode())
                .place(mwResponse.getPlace())
                .dueDate(UtilDate.formatDate(mwResponse.getDueDate()))
                .registrationDate(UtilDate.formatDate(mwResponse.getRegistrationDate()))
                .cardStatus(mwResponse.getStatusCard())
                .accountStatus(mwResponse.getStatusAccount())
                .insurance(mwResponse.getInsurance().equals("S"))
                .currency(mwResponse.getCurrencyCode())
                .amountAvailable(mwResponse.getAmountAvailable())
                .amountLimit(mwResponse.getAmountLimit())
                .limitMin(mwResponse.getLimMin())
                .limitMax(mwResponse.getLimitMax())
                .debtClose(mwResponse.getDebtClose())
                .dueDatePeriod(mwResponse.getDueDatePeriod())
                .duePaymentDatePeriod(mwResponse.getDuePaymentDatePeriod())
                .paymentAmountMinimum(mwResponse.getPaymentAmountMinimum())
                .build();
    }

    @Override
    public DetailPrepaidCardResponse convertDetails(DetailPrepaidCardMWResponse mwResponse) {
        return DetailPrepaidCardResponse.builder()
                .cardId(mwResponse.getAccountMaster())
                .cmsAccount(mwResponse.getCmsAccountNumber())
                .cardNumber(Util.obfuscateCardNumber(mwResponse.getPanNumber()))
                .holderName(mwResponse.getHolderName())
                .productCode(mwResponse.getProductCode())
                .product(mwResponse.getProduct())
                .currency(mwResponse.getCurrency())
                .registrationDate(UtilDate.formatDate(mwResponse.getRegistrationDate()))
                .dueDate(UtilDate.formatDate(mwResponse.getDueDate()))
                .cardStatus(mwResponse.getStatusCard())
                .accountStatus(mwResponse.getStatusAccount())
                .placeCode(mwResponse.getPlaceCode())
                .place(mwResponse.getPlace())
                .insurance(mwResponse.getInsurance())
                .limitMin(mwResponse.getLimitMin())
                .limitMax(mwResponse.getLimitMax())
                .build();
    }

    @Override
    public BlockCreditCardMWRequest mapperRequest(BlockCreditCardRequest request, String personId) {
        return BlockCreditCardMWRequest.builder()
                .cmsCardNumber(request.getCmsCard())
                .personId(personId)
                .codBlockType(request.getType())
                .codBlockReason(request.getType().equals(CreditCardConstans.UNBLOCK_REASON.getValue()) ?
                        CreditCardConstans.UNBLOCK_REASON.getValue() :
                        CreditCardConstans.BLOCK_REASON.getValue())
                .build();
    }

    @Override
    public AuthorizationCreditCardMWRequest mapperRequest(String personId, AuthorizationCreditCardRequest request) {
        return AuthorizationCreditCardMWRequest.builder()
                .personId(personId)
                .cmsAccountNumber(request.getCmsAccount())
                .cmsCardNumber(request.getCmsCard())
                .initDate(UtilDate.adaptDateToMWFormat(request.getPeriod().getStart()))
                .endDate(UtilDate.adaptDateToMWFormat(request.getPeriod().getEnd()))
                .amount(String.valueOf(request.getAmount()))
                .type(request.getType())
                .requestType(request.getRequestType())
                .build();
    }

    @Override
    public AvailableCreditCardResponse convertAvailable(AvailableCreditCardMWResponse mwResponse) {
        return new AvailableCreditCardResponse(mwResponse.getAvailableAmount(), mwResponse.getLimitAmount());
    }

    @Override
    public List<PeriodCreditCardResponse> convertPeriods(PeriodCreditCardMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> PeriodCreditCardResponse.builder()
                        .periodCode(mw.getIdPeriod())
                        .month(mw.getMonth())
                        .year(mw.getYear())
                        .init(UtilDate.formatDate(mw.getInitPeriod()))
                        .end(UtilDate.formatDate(mw.getEndPeriod()))
                        .build())
                .toList();
    }

    @Override
    public CashAdvanceFeeMWRequest mapperRequest(String personId, String cmsAccountNumber, BigDecimal amount) {
        return CashAdvanceFeeMWRequest.builder()
                .cmsAccountNumber(cmsAccountNumber)
                .personId(personId)
                .amount(amount)
                .build();
    }

    @Override
    public CashAdvanceFeeResponse convertResponse(CashAdvanceFeeMWResponse mwResponse) {
        return CashAdvanceFeeResponse.builder()
                .fee(mwResponse.getAmountCommission())
                .build();
    }

    @Override
    public List<LinkserCreditCardResponse> convertListCreditCard(LinkserCreditCardMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();

        return mwResponse.getData().stream()
                .map(mw ->
                        LinkserCreditCardResponse.builder()
                                .cmsCard(mw.getCmsCardNumber())
                                .cardNumber(Util.obfuscateCardNumber(mw.getPanNumber()))
                                .holderName(mw.getHolderName().trim())
                                .dueDate(convertDueDateCreditCard(mw.getDueDate()))
                                .branch(mw.getBranch())
                                .cardType(mw.getCardType().trim())
                                .statusCode(mw.getStatusCode())
                                .statusDescription(getLastWord(mw.getStatusDescription().contains(RESPONSE_TEXT_ACTIVE) ? RESPONSE_TEXT_FORMATED : RESPONSE_TEXT_BLOCKED))
                                .build())
                .toList();
    }

    private String getLastWord(String text) {
        return text.substring(text.lastIndexOf(" ") + 1);
    }

    private String convertDueDateCreditCard(String dueDate) {
        if (dueDate != null && dueDate.length() == 6) {
            String year = dueDate.substring(0, 4);
            String month = dueDate.substring(4, 6);
            return month + "/" + year;
        }
        return dueDate;
    }

    @Override
    public CashAdvanceMWRequest mapperRequestAdvance(String personId, CashAdvanceRequest request) {
        return CashAdvanceMWRequest.builder()
                .personId(personId)
                .companyId("0")
                .cmsAccountNumber(request.getCmsAccount())
                .cmsCardNumber(request.getCmsCard())
                .accountId(request.getAccountId())
                .amount(request.getAmount())
                .description(request.getDescription())
                .build();
    }

    @Override
    public CashAdvanceResponse convertResponse(CashAdvanceMWResponse mwResponse) {
        return CashAdvanceResponse.builder()
                .voucher(mwResponse.getVoucher())
                .voucherDate(UtilDate.formatDate(mwResponse.getVoucherDate()))
                .voucherTime(mwResponse.getVoucherTime())
                .amountCommission(mwResponse.getAmountCommission())
                .amountPaid(mwResponse.getAmountPaid())
                .exchangeRate(mwResponse.getExchangeRate())
                .swExchangeRate(mwResponse.getSwExchangeRate())
                .build();
    }

    @Override
    public List<CreditCardStatementsResponse> convertStatements(CreditCardStatementsMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null) {
            return Collections.emptyList();
        }
        return mwResponse.getData().stream()
                .map(mw -> CreditCardStatementsResponse.builder()
                        .cardNumber(Util.obfuscateCardNumber(mw.getCardNumber()))
                        .description(mw.getDescription().trim())
                        .currency(mw.getOriginalCurrency())
                        .mrAmount(Util.scaleToTwoDecimals(mw.getMrAmount()))
                        .mlAmount(Util.scaleToTwoDecimals(mw.getMlAmount()))
                        .transactionDate(mw.getTransactionDate())
                        .processDate(UtilDate.formatDate(mw.getProcessDate()))
                        .clientName(mw.getClientName().trim())
                        .transactionType(mw.getTransactionType())
                        .transactionTypeDesc(mw.getTransactionTypeDesc())
                        .transactionStatus(mw.getTransactionStatus().equals(TRANSACTIONS_STATUS_POSTED) ? TRANSACTIONS_STATUS_POSTED_FORMATTED : TRANSACTIONS_STATUS_NO_POSTED_FORMATTED)
                        .sequenceNumber(mw.getSequenceNumber())
                        .feeNumber(mw.getFeeNumber())
                        .paramDate(UtilDate.formatDate(mw.getParamDate()))
                        .build()
                ).toList();
    }

    @Override
    public List<PurchaseAuthResponse> convertPurchase(PurchaseAuthMWResponse mwResponse, String type) {
        if (mwResponse == null || mwResponse.getData() == null) {
            return Collections.emptyList();
        }
        List<PurchaseAuthResponse> list = mwResponse.getData().stream()
                .map(mw -> PurchaseAuthResponse.builder()
                        .processDate(UtilDate.formatDate(mw.getProcessDate()))
                        .type(mw.getType())
                        .amount(mw.getAmount())
                        .currency(mw.getCurrency())
                        .status(mw.getStatus())
                        .origin(mw.getOrigin())
                        .initDate(mw.getInitDate())
                        .endDate(mw.getEndDate())
                        .requestType(mw.getRequestType())
                        .build()
                )
                .toList();

        if (type.equals(REQUEST_TYPE_INTERNET)) {
            return list.stream().filter(
                    obj -> obj.getRequestType().equals(TYPE_INTERNET)
            ).toList();
        }

        return list.stream().filter(
                obj -> obj.getRequestType().equals(TYPE_SPECIAL)
        ).toList();
    }

    @Override
    public PayCreditCardMWRequest mapperRequest(String personId, String accountId, PayCreditCardRequest request) {
        return PayCreditCardMWRequest.builder()
                .ownerAccount(TransOwnerAccount.builder()
                        .schemeName(CreditCardConstans.PERSON_ID.getValue())
                        .personId(personId)
                        .companyId("0")
                        .build())
                .debtorAccount(TransAccount.builder()
                        .schemeName(CreditCardConstans.ACCOUNT_ID.getValue())
                        .identification(accountId)
                        .build())
                .creditorAccount(TransAccount.builder()
                        .schemeName(CreditCardConstans.TC_ACCOUNT_ID.getValue())
                        .identification(request.getTargetAccount().getId())
                        .build())
                .instructedAmount(TransAmount.builder()
                        .currency(request.getAmount().getCurrency())
                        .amount(request.getAmount().getAmount())
                        .build())
                .supplementaryData(PayCreditCardMWRequest.SupplementaryData.builder()
                        .transactionType(CreditCardConstans.TRANSACTION_TYPE.getValue())
                        .description(request.getData().getDescription())
                        .sourceOfFunds(request.getData().getSourceOfFunds())
                        .destinationOfFunds(request.getData().getDestinationOfFunds())
                        .build())
                .build();
    }

    @Override
    public PayCreditCardResponse convertPayCC(PayCreditCardMWResponse mwResponse) {
        return PayCreditCardResponse.builder()
                .status(mwResponse.getStatus())
                .transactionId(mwResponse.getTransactionNum())
                .maeId(mwResponse.getMaeId())
                .accountingEntry(mwResponse.getAccountingEntry())
                .accountingDate(UtilDate.formatDate(mwResponse.getAccountingDate()))
                .accountingTime(mwResponse.getAccountingTime())
                .amountDebited(Util.scaleToTwoDecimals(mwResponse.getAmountDebited()))
                .exchangeRateDebit(mwResponse.getExchangeRateDebit())
                .exchangeRateCredit(mwResponse.getExchangeRateCredit())
                .amount(Util.scaleToTwoDecimals(mwResponse.getAmount()))
                .currency(mwResponse.getCurrency())
                .fromAccountNumber(mwResponse.getFromAccountNumber())
                .fromHolder(mwResponse.getFromHolder())
                .description(mwResponse.getDescription())
                .fromCurrency(mwResponse.getFromCurrency())
                .toCurrency(mwResponse.getToCurrency())
                .tcAccountId(mwResponse.getTcAccountId())
                .build();
    }

    @Override
    public FeePrepaidCardMWRequest mapperRequest(String cardId, FeePrepaidCardRequest request) {
        return FeePrepaidCardMWRequest.builder()
                .accountMaster(cardId)
                .cmsAccountNumber(request.getCmsAccount())
                .amount(request.getAmount())
                .build();
    }

    @Override
    public FeePrepaidCardResponse convertResponse(FeePrepaidCardMWResponse mwResponse) {
        return FeePrepaidCardResponse.builder()
                .fee(mwResponse.getTransferFee())
                .amount(mwResponse.getInsuranceAmount())
                .build();
    }
}
