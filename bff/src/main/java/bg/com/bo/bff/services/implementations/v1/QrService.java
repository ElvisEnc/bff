package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.qr.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QRCodeRegenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QRPaymentRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrDecryptRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrListRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrDecryptResponse;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.application.dtos.response.qr.QrListResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.enums.qr.ValidParametersFilter;
import bg.com.bo.bff.commons.filters.QrOrderFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.commons.filters.SearchCriteriaFilter;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QRCodeGenerateMWRequest;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QRCodeRegenerateMWRequest;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QRPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.request.qr.mw.QrListMWRequest;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRPaymentMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.QrGeneratedPaidMW;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.QrListMWResponse;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.IQRProvider;
import bg.com.bo.bff.providers.interfaces.IQrTransactionProvider;
import bg.com.bo.bff.mappings.providers.qr.IQrMapper;
import bg.com.bo.bff.providers.models.enums.middleware.qr.QRMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.qr.QRTransactionMiddlewareError;
import bg.com.bo.bff.services.interfaces.IQrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class QrService implements IQrService {
    private final IAchAccountProvider iAchAccountProvider;
    private final IQrTransactionProvider qrTransactionProvider;
    private final IQRProvider qrProvider;
    private final IQrMapper iQrMapper;
    @Autowired
    private QrService self;

    public QrService(IAchAccountProvider iAchAccountProvider, IQrMapper iQrMapper, IQRProvider qrProvider, IQrTransactionProvider qrTransactionProvider) {
        this.iAchAccountProvider = iAchAccountProvider;
        this.iQrMapper = iQrMapper;
        this.qrProvider = qrProvider;
        this.qrTransactionProvider = qrTransactionProvider;
    }

    @Override
    public QrListResponse getQrGeneratedPaid(QrListRequest request, String personId, Map<String, String> parameters) throws IOException {
        boolean hasSearchCriteria = request.getFilters().getSearchCriteria() != null && request.getFilters().getSearchCriteria().getParameters() != null;
        if (hasSearchCriteria) {
            if (request.getFilters().getSearchCriteria().getParameters().isEmpty() || !ValidParametersFilter.isValidParams(request.getFilters().getSearchCriteria().getParameters())) {
                throw new GenericException("ParáAmmetros inválidos: BUSINESSNAME, AMOUNT y DESCRIPTION son aceptados.", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
            }
        } else {
            request.getFilters().setSearchCriteria(null);
        }

        String startDate = request.getFilters().getPeriod().getStart();
        String endDate = request.getFilters().getPeriod().getEnd();
        String key = personId + "|" + startDate + "|" + endDate;
        Boolean needAllRecords = request.getPagination() == null || request.getPagination().getPage() == 1;
        List<QrGeneratedPaid> result = self.getListQrMW(request, personId, parameters, key, needAllRecords);

        result = result.stream().filter(qr -> qr.getOperationType().equals(request.getOperationType())).collect(Collectors.toList());
        result = new QrOrderFilter(request.getOrder()).apply(result);
        if (request.getFilters().getSearchCriteria() != null && !request.getFilters().getSearchCriteria().getValue().trim().isEmpty()) {
            result = new SearchCriteriaFilter<QrGeneratedPaid>(request.getFilters().getSearchCriteria().getParameters(), request.getFilters().getSearchCriteria().getValue()).apply(result);
        }

        QrListResponse response = QrListResponse.builder()
                .totalRegistries(result.size())
                .page(request.getPagination() != null ? request.getPagination().getPage() : 1)
                .totalByPage(request.getPagination() != null ? request.getPagination().getPageSize() : result.size())
                .build();

        if (request.getPagination() != null) {
            int page = request.getPagination().getPage();
            int pageSize = request.getPagination().getPageSize();
            result = new PageFilter(page, pageSize).apply(result);
            response.setTotalByPage(result.size());
        }

        response.setData(result);
        return response;
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.QR_GENERATED_PAID, key = "#key", condition = "#isInitial == false")},
            put = {@CachePut(value = CacheConstants.QR_GENERATED_PAID, key = "#key", condition = "#isInitial == true")})
    public List<QrGeneratedPaid> getListQrMW(QrListRequest request, String personId, Map<String, String> parameters, String key, Boolean isInitial) throws IOException {
        QrListMWRequest mwRequest = iQrMapper.mapperRequest(personId, request);
        QrListMWResponse qrListMWResponse = iAchAccountProvider.getListQrGeneratePaidMW(mwRequest, personId, parameters);
        List<QrGeneratedPaid> list = new ArrayList<>();
        if (qrListMWResponse.getData() != null) {
            for (QrGeneratedPaidMW generatedPaidMW : qrListMWResponse.getData()) {
                list.add(iQrMapper.convert(generatedPaidMW));
            }
        }
        return list;
    }

    @Override
    public QRCodeGenerateResponse generateQR(QRCodeGenerateRequest request, Map<String, String> parameters) throws IOException {
        QRCodeGenerateMWRequest requestMW = iQrMapper.convert(request);
        return qrProvider.generate(requestMW, parameters);
    }

    @Override
    public QRCodeGenerateResponse regenerateQR(QRCodeRegenerateRequest request, Map<String, String> parameter) throws IOException {
        QRCodeRegenerateMWRequest requestMW = this.iQrMapper.convert(request);
        return this.qrProvider.regenerate(requestMW, parameter);
    }

    @Override
    public QrDecryptResponse decryptQR(QrDecryptRequest request, Map<String, String> parameter) throws IOException {
        QRCodeRegenerateMWRequest requestMW = this.iQrMapper.convertDecrypt(request);
        QRCodeGenerateResponse response = this.qrProvider.decrypt(requestMW, parameter);
        QrDecryptResponse result = iQrMapper.convertDecryptResponse(response);
        if (UtilDate.isDateOutOfDate(result.getExpirationDate())) {
            throw new GenericException(QRMiddlewareError.QR_EXPIRED);
        }
        return result;
    }

    @Override
    public QRPaymentMWResponse qrPayment(QRPaymentRequest request, String personId, String accountId, Map<String, String> parameter) throws IOException {
        QRPaymentMWRequest requestMW = iQrMapper.convert(request, personId, accountId);
        QRPaymentMWResponse result = this.qrTransactionProvider.qrPayment(requestMW, parameter);
        result.getData().getReceiptDetail().setAccountingDate(UtilDate.formatDate(result.getData().getReceiptDetail().getAccountingDate()));
        if (!Objects.equals(result.getData().getStatus(), "PENDING")) {
            return result;
        } else {
            throw new GenericException(QRTransactionMiddlewareError.MDWGQM_PENDING);
        }
    }
}
