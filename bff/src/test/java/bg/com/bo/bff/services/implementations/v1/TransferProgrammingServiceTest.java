package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.transfers.programming.DeleteTransferResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.PaymentsPlanResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.PaymentsPlanResponseFixture;
import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponseFixture;
import bg.com.bo.bff.mappings.providers.transfers.programming.TransferProgrammingMapper;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.DeleteTransferMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.PaymentsPlanMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.PaymentsPlanMDWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.ProgrammedTransferMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.ProgrammedTransferMDWResponseFixture;
import bg.com.bo.bff.providers.interfaces.ITransferProgrammingProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferProgrammingServiceTest {

    @InjectMocks
    private TransferProgrammingService service;

    @Mock
    private ITransferProgrammingProvider provider;

    @Spy
    private TransferProgrammingMapper mapper;

    @Test
    void getTransfersOK() throws IOException {
        List<ProgrammedTransfersResponse> expected = ProgrammedTransfersResponseFixture.withDefaults();
        ProgrammedTransferMDWResponse mdwExpected = ProgrammedTransferMDWResponseFixture.withDefaults();

        when(provider.getProgrammedTransfer(any())).thenReturn(mdwExpected);
        when(mapper.convertTransferListResponse(mdwExpected)).thenReturn(expected);

        List<ProgrammedTransfersResponse> response = service.getTransfers("1234");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getTransfersEmptyMDWResponse() throws IOException {
        List<ProgrammedTransfersResponse> expected = ProgrammedTransfersResponseFixture.withDefaults();

        when(provider.getProgrammedTransfer(any())).thenReturn(null);
        when(mapper.convertTransferListResponse(any())).thenReturn(expected);

        List<ProgrammedTransfersResponse> response = service.getTransfers("1234");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getPaymentsPlanOK() throws IOException {
        List<PaymentsPlanResponse> expected = PaymentsPlanResponseFixture.withDefaults();
        PaymentsPlanMDWResponse mdwExpected = PaymentsPlanMDWResponseFixture.withDefaults();

        when(provider.getPaymentsPlan(any())).thenReturn(mdwExpected);
        when(mapper.convertPaymentsPlanResponse(mdwExpected)).thenReturn(expected);

        List<PaymentsPlanResponse> response = service.getPaymentsPlan("1234");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void getPaymentsEmptyMDWResponse() throws IOException {
        List<PaymentsPlanResponse> expected = PaymentsPlanResponseFixture.withDefaults();

        when(provider.getPaymentsPlan(any())).thenReturn(null);
        when(mapper.convertPaymentsPlanResponse(any())).thenReturn(expected);

        List<PaymentsPlanResponse> response = service.getPaymentsPlan("1234");

        assertEquals(expected.size(), response.size());
    }

    @Test
    void deleteProgrammedTransfer() throws IOException {
        DeleteTransferMDWResponse mdwExpected = DeleteTransferMDWResponse.builder()
                .data(
                        DeleteTransferMDWResponse.DeleteTransfer.builder()
                                .desError("OK")
                                .codError("COD000")
                                .build()
                )
                .build();
        DeleteTransferResponse expected = DeleteTransferResponse.builder()
                .titulo("OK")
                .mensaje("La programación de transferencia será cancelada permanentemente, recuerda que una vez cancelada no podrá habilitarse de nuevo.")
                .build();

        when(provider.deleteTransfer(any(), any())).thenReturn(mdwExpected);
        when(mapper.convertDeleteResponse(mdwExpected)).thenReturn(expected);

        DeleteTransferResponse response = service.deleteTransfer("123", "321");

        assertEquals(expected.getMensaje(), response.getMensaje());
    }

    @Test
    void deleteProgrammedTransferError() throws IOException {
        DeleteTransferMDWResponse mdwExpected = DeleteTransferMDWResponse.builder()
                .data(
                        DeleteTransferMDWResponse.DeleteTransfer.builder()
                                .desError("Error")
                                .codError("COD001")
                                .build()
                )
                .build();
        DeleteTransferResponse expected = DeleteTransferResponse.builder()
                .titulo("Error")
                .mensaje("Algo salio mal al intentar cancela la programación de transferencia.")
                .build();

        when(provider.deleteTransfer(any(), any())).thenReturn(mdwExpected);
        when(mapper.convertDeleteResponse(mdwExpected)).thenReturn(expected);

        DeleteTransferResponse response = service.deleteTransfer("123", "321");

        assertEquals(expected.getMensaje(), response.getMensaje());
    }

}