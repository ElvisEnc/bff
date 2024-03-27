package bg.com.bo.bff.services.interfaces;

import org.springframework.http.ResponseEntity;

public interface IExportService {
    ResponseEntity<byte[]> getPdf();
    ResponseEntity<byte[]> getCsv();
}
