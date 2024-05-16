package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrListRequest;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.application.dtos.response.qr.QrListResponse;
import bg.com.bo.bff.commons.constants.CacheConstants;
import bg.com.bo.bff.commons.filters.OrderFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.providers.dtos.requests.QRCodeGenerateMWRequest;
import bg.com.bo.bff.providers.dtos.responses.qr.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.dtos.responses.qr.QrGeneratedPaidMW;
import bg.com.bo.bff.providers.dtos.responses.qr.QrListMWResponse;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.IQRProvider;
import bg.com.bo.bff.providers.mappings.qr.IQrMapper;
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
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class QrService implements IQrService {
    private final IAchAccountProvider iAchAccountProvider;
    private final IQrMapper iQrMapper;
    private final IQRProvider qrProvider;
    @Autowired
    private QrService self;

    public QrService(IAchAccountProvider iAchAccountProvider, IQrMapper iQrMapper, IQRProvider qrProvider) {
        this.iAchAccountProvider = iAchAccountProvider;
        this.iQrMapper = iQrMapper;
        this.qrProvider = qrProvider;
    }

    @Override
    public QrListResponse getQrGeneratedPaid(QrListRequest request, Integer personId, Map<String, String> parameters) throws IOException {
        String startDate = request.getFilters().getPeriod().getStart();
        String endDate = request.getFilters().getPeriod().getEnd();
        String key = personId + "|" + startDate + "|" + endDate;
        Boolean needAllRecords = request.getPagination() == null || request.getPagination().getPage() == 1;
        List<QrGeneratedPaid> result = self.getListQrMW(request, personId, parameters, key, needAllRecords);

        result = new OrderFilter(request.getOrder()).apply(result);

        Map<Boolean, List<QrGeneratedPaid>> partitionedLists = result.stream()
                .collect(Collectors.partitioningBy(q -> q.getOperationType().equals("1")));
        List<QrGeneratedPaid> operationTypeGeneratedList = partitionedLists.get(true);
        List<QrGeneratedPaid> operationTypePaidList = partitionedLists.get(false);

        QrListResponse response = QrListResponse.builder()
                .totalGenerated(operationTypeGeneratedList.size())
                .totalPaid(operationTypePaidList.size())
                .build();

        if (request.getPagination() != null) {
            int page = request.getPagination().getPage();
            int pageSize = request.getPagination().getPageSize();
            operationTypeGeneratedList = new PageFilter(page, pageSize).apply(operationTypeGeneratedList);
            operationTypePaidList = new PageFilter(page, pageSize).apply(operationTypePaidList);
        }

        response.setGenerated(operationTypeGeneratedList);
        response.setPaid(operationTypePaidList);
        return response;
    }

    @Caching(cacheable = {@Cacheable(value = CacheConstants.QR_GENERATED_PAID, key = "#key", condition = "#isInitial == false")},
            put = {@CachePut(value = CacheConstants.QR_GENERATED_PAID, key = "#key", condition = "#isInitial == true")})
    public List<QrGeneratedPaid> getListQrMW(QrListRequest request, Integer personId, Map<String, String> parameters, String key, Boolean isInitial) throws IOException {
        QrListMWResponse qrListMWResponse = iAchAccountProvider.getListQrGeneratePaidMW(request, personId, parameters);
        List<QrGeneratedPaid> list = new ArrayList<>();
        for (QrGeneratedPaidMW generatedPaidMW : qrListMWResponse.getData()) {
            list.add(iQrMapper.convert(generatedPaidMW));
        }
        return list;
    }

    @Override
    public QRCodeGenerateResponse generateQR(QRCodeGenerateRequest request, Map<String, String> parameters) throws IOException {

        QRCodeGenerateMWRequest requestMW = iQrMapper.convert(request);
        return qrProvider.generate(requestMW, parameters);
    }
}
