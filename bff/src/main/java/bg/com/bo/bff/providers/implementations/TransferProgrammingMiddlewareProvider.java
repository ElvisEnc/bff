package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.commons.enums.config.provider.ProjectNameMW;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.transfers.programming.SaveTransferMDWRequest;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.DeleteTransferMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.PaymentsPlanMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.ProgrammedTransferMDWResponse;
import bg.com.bo.bff.providers.dtos.response.transfers.programming.SaveTransferMDWResponse;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.ITransferProgrammingProvider;
import bg.com.bo.bff.providers.models.enums.middleware.transfers.programming.TransferProgrammingMDWError;
import bg.com.bo.bff.providers.models.enums.middleware.transfers.programming.TransfersProgrammingMDWServices;
import bg.com.bo.bff.providers.models.middleware.HeadersMW;
import bg.com.bo.bff.providers.models.middleware.MiddlewareProvider;
import bg.com.bo.bff.providers.models.middleware.response.handler.ByMwErrorResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransferProgrammingMiddlewareProvider extends MiddlewareProvider<TransferProgrammingMDWError> implements ITransferProgrammingProvider {

    private final String baseURL;

    private final HttpServletRequest httpServletRequest;

    public TransferProgrammingMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory, HttpServletRequest httpServletRequest) {
        super(
                ProjectNameMW.TRANSFER_PROGRAMMING_MANAGER,
                TransferProgrammingMDWError.class,
                tokenMiddlewareProvider,
                middlewareConfig,
                httpClientFactory,
                middlewareConfig.getClientTransferProgramming()
        );
        baseURL = middlewareConfig.getUrlBase() + ProjectNameMW.TRANSFER_PROGRAMMING_MANAGER.getName();
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ProgrammedTransferMDWResponse getProgrammedTransfer(String personId) throws IOException {
        String url = baseURL + String.format(
                TransfersProgrammingMDWServices.GET_TRANSFERS_LIST.getServiceUrl(),
                personId
        );
        ByMwErrorResponseHandler<ProgrammedTransferMDWResponse> responseHandler = ByMwErrorResponseHandler.instance(TransferProgrammingMDWError.TPM_0002);
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), ProgrammedTransferMDWResponse.class, responseHandler);
    }

    @Override
    public PaymentsPlanMDWResponse getPaymentsPlan(String transferId) throws IOException {
        String url = baseURL + String.format(
                TransfersProgrammingMDWServices.GET_PAYMENTS_PLAN.getServiceUrl(),
                transferId
        );
        ByMwErrorResponseHandler<PaymentsPlanMDWResponse> responseHandler = ByMwErrorResponseHandler.instance(TransferProgrammingMDWError.TPM_0002);
        return get(url, HeadersMW.getDefaultHeaders(httpServletRequest), PaymentsPlanMDWResponse.class, responseHandler);
    }

    @Override
    public DeleteTransferMDWResponse deleteTransfer(String personId, String transferId) throws IOException {
        String url = baseURL + String.format(
                TransfersProgrammingMDWServices.DELETE_PROGRAMMED_TRANSFER.getServiceUrl(),
                personId,
                transferId
        );
        return delete(url, HeadersMW.getDefaultHeaders(httpServletRequest), DeleteTransferMDWResponse.class);
    }

    @Override
    public SaveTransferMDWResponse saveTransfer(SaveTransferMDWRequest request) throws IOException {
        String url = baseURL + TransfersProgrammingMDWServices.SAVE_TRANSFER.getServiceUrl();
        return post(url, HeadersMW.getDefaultHeaders(httpServletRequest), request, SaveTransferMDWResponse.class);
    }


}
