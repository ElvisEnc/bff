package bg.com.bo.bff.mappings.providers.transfers.programming;

import bg.com.bo.bff.application.dtos.request.transfers.programming.SaveTransferRequest;
import bg.com.bo.bff.application.dtos.response.transfers.programming.DeleteTransferResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.PaymentsPlanResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.SaveProgrammedTransferResponse;
import bg.com.bo.bff.providers.dtos.request.transfers.programming.SaveTransferMDWRequest;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.DeleteTransferMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.PaymentsPlanMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.ProgrammedTransferMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.SaveTransferMDWResponse;
import org.springframework.stereotype.Component;

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

    @Override
    public DeleteTransferResponse convertDeleteResponse(DeleteTransferMDWResponse mdwResponse) {
        if (!mdwResponse.getData().getCodError().equals("COD000")) {
            return DeleteTransferResponse.builder()
                    .titulo("Error")
                    .mensaje("Algo salio mal al intentar cancela la programación de transferencia.")
                    .build();
        }
        return DeleteTransferResponse.builder()
                .titulo("Transferencia Cancelada")
                .mensaje("La programación de transferencia será cancelada permanentemente, recuerda que una vez cancelada no podrá habilitarse de nuevo.")
                .build();
    }

    @Override
    public SaveTransferMDWRequest convertSaveRequest(SaveTransferRequest request, String personId) {
        return SaveTransferMDWRequest.builder()
                .personId(personId)
                .originJtsOid(request.getOriginJtsOid())
                .destinationAccountNumber(request.getDestinationAccountNumber())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .transferType(request.getTransferType())
                .description(request.getDescription())
                .frequencyCode(request.getFrequencyCode())
                .initDate(request.getInitDate())
                .endDate(request.getEndDate())
                .userRegistry(request.getUserRegistry())
                .destinationJtsOid(request.getDestinationJtsOid())
                .listCode(request.getListCode())
                .codEif(request.getCodEif())
                .destinationFounds(request.getDestinationFounds())
                .originFounds(request.getOriginFounds())
                .reason(request.getReason())
                .destinationName(request.getDestinationName())
                .bankName(request.getBankName())
                .uifControl(request.getUifControl())
                .build();
    }

    @Override
    public SaveProgrammedTransferResponse convertSaveResponse(SaveTransferMDWResponse mdwResponse) {
        if (!mdwResponse.getData().getCodError().equals("COD000")) {
            return SaveProgrammedTransferResponse.builder()
                    .titulo("Error")
                    .mensaje("Algo salio mal al intentar registrar la programación de transferencia.")
                    .build();
        }
        return SaveProgrammedTransferResponse.builder()
                .titulo("Registro Existoso")
                .mensaje("Registro exitoso, el débito del monto programado será relizado de manera automática todos los días a horas 05:00 de la mañana.")
                .build();
    }


}
