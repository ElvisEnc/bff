package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.ExportResponse;
import org.springframework.http.ResponseEntity;

public interface IExportService {
    ExportResponse getPdf();
    ExportResponse getCsv();
}
