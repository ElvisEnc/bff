package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.services.interfaces.IExportService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@Service
public class ExportService implements IExportService {

    public ResponseEntity<byte[]> getPdf() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("template.pdf").getFile());
            PDDocument document = Loader.loadPDF(file);
            PDPage page = document.getPage(0);

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Hello, World!");
            contentStream.endText();
            contentStream.close();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "example.pdf");

            return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> getCsv() {
        String[] header = {"ID", "Nombre", "Edad"};
        String[][] data = {
                {"1", "Juan", "30"},
                {"2", "Maria", "25"},
                {"3", "Carlos", "35"}
        };
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader(header).withDelimiter(';'))) {
            for (String[] row : data) {
                csvPrinter.printRecord((Object[]) row);
            }
            csvPrinter.close();
            byte[] csvBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("text", "csv"));
            headers.setContentDispositionFormData("filename", "data.csv");

            return ResponseEntity.ok().headers(headers).body(csvBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
