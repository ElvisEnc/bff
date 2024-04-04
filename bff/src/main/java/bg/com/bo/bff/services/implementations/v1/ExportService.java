package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.ExportResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.IExportService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class ExportService implements IExportService {

    public ExportResponse getPdf() {
        try (InputStream inputStream = getClass().getResourceAsStream("/template.pdf")) {
            PDDocument document = Loader.loadPDF(inputStream.readAllBytes());
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

            String base64 = Util.encodeByteArrayToBase64(byteArrayOutputStream.toByteArray());

            ExportResponse response = new ExportResponse();
            response.setData(base64);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException();
        }
    }

    public ExportResponse getCsv() {
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

            String base64 = Util.encodeByteArrayToBase64(csvBytes);

            ExportResponse response = new ExportResponse();
            response.setData(base64);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException();
        }
    }
}
