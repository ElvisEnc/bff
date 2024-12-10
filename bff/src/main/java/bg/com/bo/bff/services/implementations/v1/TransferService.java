package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.transfer.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.response.transfer.Pcc01Response;
import bg.com.bo.bff.application.dtos.response.transfer.TransferResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.transfer.ITransferMapper;
import bg.com.bo.bff.mappings.providers.transfer.TransferMWtMapper;
import bg.com.bo.bff.providers.dtos.request.transfer.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.*;
import bg.com.bo.bff.providers.interfaces.ITransferACHProvider;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.models.enums.middleware.transfer.TransferMiddlewareError;
import bg.com.bo.bff.services.interfaces.ICryptoService;
import bg.com.bo.bff.services.interfaces.ITransferService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class TransferService implements ITransferService {
    private final ICryptoService cryptoService;
    private final ITransferProvider transferProvider;
    private final ITransferACHProvider transferACHProvider;
    private final TransferMWtMapper transferMapper;
    private final ITransferMapper mapper;

    public TransferService(ICryptoService cryptoService, ITransferProvider transferProvider, ITransferACHProvider transferACHProvider, TransferMWtMapper transferMapper, ITransferMapper mapper) {
        this.cryptoService = cryptoService;
        this.transferProvider = transferProvider;
        this.transferACHProvider = transferACHProvider;
        this.transferMapper = transferMapper;
        this.mapper = mapper;
    }

    @Override
    public TransferResponse transferOwnAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        cryptoService.validateCrypto(transferRequest.getData().getDescription(), parameter);
        TransferMWRequest requestMW = transferMapper.convert("own", personId, accountId, transferRequest);
        TransferMWResponse responseMW = transferProvider.transferOwnAccount(requestMW, parameter);
        if (!Objects.equals(responseMW.getData().getStatus(), "PENDING")) {
            return mapper.convert(responseMW);
        } else {
            throw new GenericException(TransferMiddlewareError.MDWTRM_PENDING);
        }
    }

    @Override
    public TransferResponse transferThirdAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        cryptoService.validateCrypto(transferRequest.getData().getDescription(), parameter);
        TransferMWRequest requestMW = transferMapper.convert("own", personId, accountId, transferRequest);
        TransferMWResponse responseMW = transferProvider.transferThirdAccount(requestMW, parameter);
        if (!Objects.equals(responseMW.getData().getStatus(), "PENDING")) {
            return mapper.convert(responseMW);
        } else {
            throw new GenericException(TransferMiddlewareError.MDWTRM_PENDING);
        }
    }

    @Override
    public TransferResponse transferWallet(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        cryptoService.validateCrypto(transferRequest.getData().getDescription(), parameter);
        TransferMWRequest requestMW = transferMapper.convert("own", personId, accountId, transferRequest);
        TransferWalletMWResponse responseMW = transferProvider.transferWalletAccount(requestMW, parameter);
        if (!Objects.equals(responseMW.getData().getStatus(), "PENDING")) {
            return mapper.convert(responseMW);
        } else {
            throw new GenericException(TransferMiddlewareError.MDWTRM_PENDING);
        }
    }

    @Override
    public TransferResponse transferAchAccount(String personId, String accountId, TransferRequest transferRequest, Map<String, String> parameter) throws IOException {
        cryptoService.validateCrypto(transferRequest.getData().getDescription(), parameter);
        TransferMWRequest requestMW = transferMapper.convert("ach", personId, accountId, transferRequest);
        TransferAchMwResponse responseMW = transferACHProvider.transferAchAccount(requestMW, parameter);
        if (!Objects.equals(responseMW.getData().getStatus(), "PENDING")) {
            return mapper.convert(responseMW);
        } else {
            throw new GenericException(TransferMiddlewareError.MDWTRM_PENDING);
        }
    }

    @Override
    public Pcc01Response makeControl(String personId, String accountId, Pcc01Request request, Map<String, String> parameter) throws IOException {
        Pcc01MWRequest mwRequest = mapper.mapperRequest(personId, accountId, request);
        return transferProvider.validateControl(mwRequest, parameter);
    }
}
