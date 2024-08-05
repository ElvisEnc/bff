package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.dtos.request.account.statement.ExportRequest;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;
import bg.com.bo.bff.providers.interfaces.IAccountStatementPdfProvider;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class AccountStatementPdfProvider implements IAccountStatementPdfProvider {
    private static final Logger LOGGER = LogManager.getLogger(AccountStatementPdfProvider.class.getName());

    @Override
    public byte[] generatePdf(List<AccountStatementsMWResponse.AccountStatementMW> accountReportData, ExportRequest request, String accountId) throws IOException {
        String range = " desde  " + request.getFilters().getStartDate() + "  hasta  " + request.getFilters().getEndDate();

        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            float pageWidth = document.getPageSize().getWidth();
            float margin = document.leftMargin() + document.rightMargin();
            float rectangleWidth = (pageWidth - margin) * 0.99f;
            float rectangleHeight = 40;
            float startX = (pageWidth - rectangleWidth) / 2;
            float startY = document.getPageSize().getHeight() - document.topMargin() - rectangleHeight + 10;
            float cornerRadius = 10f;

            Color black = new Color(0, 0, 0);
            Color white = new Color(255, 255, 255);
            Color green = new Color(18, 81, 17);

            PdfContentByte canvas = writer.getDirectContent();
            canvas.setLineWidth(1.5f);
            canvas.setColorStroke(green);
            canvas.setColorFill(green);
            canvas.roundRectangle(startX, startY, rectangleWidth, rectangleHeight, cornerRadius);
            canvas.fillStroke();

            try {
                Image image = Image.getInstance(getClass().getResourceAsStream("/logo.png").readAllBytes());
                image.scaleAbsolute(150f, 30f);
                image.setAbsolutePosition(startX + 10, startY + (rectangleHeight - 30) / 2);
                canvas.addImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(e);
            }

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, white);
            Paragraph para = new Paragraph("Extracto " + range, font);
            float titleBaseLine = startY + rectangleHeight / 2 + (font.getCalculatedSize() / 2) - 11;
            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, para, startX + rectangleWidth / 2, titleBaseLine, 0);

            document.add(new Paragraph(" ", new Font(Font.HELVETICA, rectangleHeight - 10)));

            PdfPTable table = new PdfPTable(new float[]{2, 2, 3, 2, 1, 2, 5});
            table.setWidthPercentage(99);

            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, green);
            Font fontData = FontFactory.getFont(FontFactory.HELVETICA, 9, black);

            if (!accountReportData.isEmpty()) {
                String[] headers = {"Transacción", "Canal", "Fecha y Hora", "Monto", "Tipo", "Balance", "Descripción"};
                for (String header : headers) {
                    table.addCell(createCell(header, fontHeader));
                }
                for (AccountStatementsMWResponse.AccountStatementMW transaction : accountReportData) {
                    table.addCell(createCell(String.valueOf(transaction.getSeatNumber()), fontData));
                    table.addCell(createCell(transaction.getBranchOffice(), fontData));
                    table.addCell(createCell(transaction.getProcessDate() + " " + transaction.getAccountingTime(), fontData));
                    table.addCell(createCell(String.valueOf(transaction.getAmount()), fontData));
                    table.addCell(createCell(transaction.getMoveType(), fontData));
                    table.addCell(createCell(String.valueOf(transaction.getCurrentBalance()), fontData));
                    table.addCell(createCell(transaction.getDescription(), fontData));
                }
            }
            else {
                Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

                Paragraph accountSubtitle = new Paragraph("SIN REGISTROS", subtitleFont);
                accountSubtitle.setSpacingAfter(5);
                document.add(accountSubtitle);
            }

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
            LOGGER.error(e);
        }
        return out.toByteArray();
    }

    private PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(10);
        cell.setPaddingTop(10);
        cell.setPaddingLeft(5);
        cell.setPaddingRight(5);
        return cell;
    }
}
