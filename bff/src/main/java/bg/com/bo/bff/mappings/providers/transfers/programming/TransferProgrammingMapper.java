package bg.com.bo.bff.mappings.providers.transfers.programming;

import bg.com.bo.bff.application.dtos.response.transfers.programming.PaymentsPlanResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.PaymentsPlanMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.ProgrammedTransferMDWResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Component
public class TransferProgrammingMapper implements ITransferProgrammingMapper {

    @Override
    public List<ProgrammedTransfersResponse> convertTransferListResponse(ProgrammedTransferMDWResponse mdwResponse) {
        if (mdwResponse == null) {
            return Collections.emptyList();
        }
        return mdwResponse.getData().stream()
                .map(
                        transferObj -> ProgrammedTransfersResponse.builder()
                                .transferId(transferObj.getTransferId())
                                .personId(transferObj.getPersonId())
                                .originJtsOid(transferObj.getOriginJtsOid())
                                .destinationAccount(transferObj.getDestinationAccount())
                                .amount(transferObj.getAmount())
                                .currency(transferObj.getCurrency())
                                .transferType(transferObj.getTransferType())
                                .description(transferObj.getDescription())
                                .frequencyCode(transferObj.getFrequencyCode())
                                .initDate(transferObj.getInitDate())
                                .endDate(transferObj.getEndDate())
                                .userRegistry(transferObj.getUserRegistry())
                                .registryDate(transferObj.getRegistryDate())
                                .userModification(transferObj.getUserModification())
                                .modificationDate(transferObj.getModificationDate())
                                .tzLock(transferObj.getTzLock())
                                .destinationJtsOid(transferObj.getDestinationJtsOid())
                                .listCode(transferObj.getListCode())
                                .codEif(transferObj.getCodEif())
                                .destinationFounds(transferObj.getDestinationFounds())
                                .originFounds(transferObj.getOriginFounds())
                                .reason(transferObj.getReason())
                                .status(transferObj.getStatus())
                                .destinationName(transferObj.getDestinationName())
                                .bankName(transferObj.getBankName())
                                .uifControl(transferObj.getUifControl())
                                .feeNumber(transferObj.getFeeNumber())
                                .build()
                ).toList();
    }

    @Override
    public List<PaymentsPlanResponse> convertPaymentsPlanResponse(PaymentsPlanMDWResponse mdwResponse) {
        if (mdwResponse == null) {
            return Collections.emptyList();
        }

        return mdwResponse.getData().stream()
                .map(
                        paymentObj -> PaymentsPlanResponse.builder()
                                .paymentId(paymentObj.getPaymentId())
                                .progTransferId(paymentObj.getProgTransferId())
                                .trackingProcess(paymentObj.getTrackingProcess())
                                .registerDate(paymentObj.getRegisterDate())
                                .amount(paymentObj.getAmount())
                                .currency(paymentObj.getCurrency())
                                .status(paymentObj.getStatus())
                                .processError(paymentObj.getProcessError())
                                .voucher(paymentObj.getVoucher())
                                .accountingEntry(paymentObj.getAccountingEntry())
                                .accountEntryDate(paymentObj.getAccountEntryDate())
                                .branch(paymentObj.getBranch())
                                .tzLock(paymentObj.getTzLock())
                                .orderCode(paymentObj.getOrderCode())
                                .batchNumber(paymentObj.getBatchNumber())
                                .ordinal(paymentObj.getOrdinal())
                                .build()
                ).toList();
    }
}
