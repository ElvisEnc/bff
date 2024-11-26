package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.transfer.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.response.transfer.Pcc01Response;
import bg.com.bo.bff.application.dtos.response.transfer.TransferResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.pcc01.Pcc01Mapper;
import bg.com.bo.bff.mappings.providers.transfer.ITransferMapper;
import bg.com.bo.bff.mappings.providers.transfer.TransferMWtMapper;
import bg.com.bo.bff.providers.dtos.request.transfer.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.Pcc01MWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferAchMwResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferWalletMWResponse;
import bg.com.bo.bff.providers.interfaces.ITransferACHProvider;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.models.enums.middleware.transfer.TransferMiddlewareError;
import bg.com.bo.bff.services.interfaces.ITransferService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class TransferService implements ITransferService {
    private final ITransferProvider transferProvider;
    private final ITransferACHProvider transferACHProvider;
    private final TransferMWtMapper transferMapper;
    private final Pcc01Mapper pcc01Mapper;
    private final ITransferMapper mapper;

    public TransferService(ITransferProvider transferProvider, ITransferACHProvider transferACHProvider, TransferMWtMapper transferMapper, Pcc01Mapper pcc01Mapper, ITransferMapper mapper) {
        this.transferProvider = transferProvider;
        this.transferACHProvider = transferACHProvider;
        this.transferMapper = transferMapper;
        this.pcc01Mapper = pcc01Mapper;
        this.mapper = mapper;
    }

    @Override
    public TransferResponse transferOwnAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        TransferMWRequest requestMW = transferMapper.convert("own", personId, accountId, transferRequest);
        TransferMWResponse responseMW = transferProvider.transferOwnAccount(requestMW, parameter);
        if (!Objects.equals(responseMW.getData().getStatus(), "PENDING")) {
            return mapper.convert(responseMW);
        } else {
            TransferMiddlewareError error = TransferMiddlewareError.MDWTRM_PENDING;
            throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
        }
    }

    @Override
    public TransferResponse transferThirdAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        TransferMWRequest requestMW = transferMapper.convert("own", personId, accountId, transferRequest);
        TransferMWResponse responseMW = transferProvider.transferThirdAccount(requestMW, parameter);
        if (!Objects.equals(responseMW.getData().getStatus(), "PENDING")) {
            return mapper.convert(responseMW);
        } else {
            TransferMiddlewareError error = TransferMiddlewareError.MDWTRM_PENDING;
            throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
        }
    }

    @Override
    public TransferResponse transferWallet(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        TransferMWRequest requestMW = transferMapper.convert("own", personId, accountId, transferRequest);
        TransferWalletMWResponse responseMW = transferProvider.transferWalletAccount(requestMW, parameter);
        if (!Objects.equals(responseMW.getData().getStatus(), "PENDING")) {
            return mapper.convert(responseMW);
        } else {
            TransferMiddlewareError error = TransferMiddlewareError.MDWTRM_PENDING;
            throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
        }
    }

    @Override
    public TransferResponse transferAchAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        TransferMWRequest requestMW = transferMapper.convert("ach", personId, accountId, transferRequest);
        TransferAchMwResponse responseMW = transferACHProvider.transferAchAccount(requestMW, parameter);
        return mapper.convert(responseMW);
    }

    @Override
    public Pcc01Response makeControl(String personId, String accountId, Pcc01Request request, Map<String, String> parameter) throws IOException {
        Pcc01MWRequest mwRequest = mapper.mapperRequest(personId, accountId, request);
        return transferProvider.validateControl(mwRequest, parameter);
    }
}
