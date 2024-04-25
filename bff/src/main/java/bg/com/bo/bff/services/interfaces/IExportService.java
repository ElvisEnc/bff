package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.export.account.statement.ExportRequest;
import bg.com.bo.bff.application.dtos.response.ExportResponse;

import java.io.IOException;

public interface IExportService {
    ExportResponse generateReport(ExportRequest request, String account) throws IOException;
}
