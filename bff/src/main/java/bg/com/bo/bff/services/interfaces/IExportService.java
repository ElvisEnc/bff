package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.ExportRequest;
import bg.com.bo.bff.application.dtos.response.ExportResponse;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;

import java.io.IOException;
import java.util.List;

public interface IExportService {
    ExportResponse generateReport(ExportRequest request, String account) throws IOException;
}
