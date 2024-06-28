package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.providers.dtos.response.TransferResponseMD;
import bg.com.bo.bff.providers.interfaces.IGenerateImage;
import bg.com.bo.bff.mappings.providers.transfer.TransferDetailsMapper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.ProviderException;
import java.util.Base64;

@Service
public class GenerateImageProvider implements IGenerateImage {
    private static final String BASE64_PREFIX = "data:image/png;base64,";
    private static final String DEFAULT_IMAGE_FORMAT = "PNG";

    public final String layoutBG;

    public GenerateImageProvider() {
        try (InputStream file =  getClass().getResourceAsStream("/layout_comprobante.txt")) {
            assert file != null;
            this.layoutBG = new String(file.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int calculateCenterX(String text, int backgroundWidth, FontMetrics metrics) {
        int textWidth = metrics.stringWidth(text);
        return (backgroundWidth - textWidth) / 2;
    }

    @Override
    public String generateImage(TransferResponseMD response) {
        TransferDetailsMapper transferDetailsProvider = new TransferDetailsMapper(response);
        BufferedImage background = decodeBase64Image(layoutBG);
        Graphics2D g = (Graphics2D) background.getGraphics();

        Font fontTitle = new Font("Arial", Font.BOLD, 32);
        Font fontCurrency = new Font("Arial", Font.BOLD, 46);
        Font fontDescription = new Font("Arial", Font.BOLD, 22);
        Font fontAccountDetail = new Font("Arial", Font.BOLD, 24);
        String black = "#000000";
        String green = "#80bc00";
        String gray = "#808080";

        g.setColor(Color.decode(black));
        g.setFont(fontTitle);
        FontMetrics metrics = g.getFontMetrics(fontTitle);
        FontMetrics entryMetrics = g.getFontMetrics(fontDescription);

        int xTitle = calculateCenterX(transferDetailsProvider.getTitle(), background.getWidth(), metrics);
        int xDescription = calculateCenterX(transferDetailsProvider.getDescription(), background.getWidth(), metrics);
        int xCurrencyAmount = calculateCenterX(transferDetailsProvider.getCurrencyAmount(), background.getWidth(), metrics);
        int xDateHour = calculateCenterX(transferDetailsProvider.getDateHour(), background.getWidth(), entryMetrics);
        int xAccountingEntry = calculateCenterX(transferDetailsProvider.getNroEntry(), background.getWidth(), entryMetrics);

        try {
            g.drawString(transferDetailsProvider.getTitle(), xTitle, 200);
            g.setColor(Color.decode(green));
            g.setFont(fontCurrency);
            g.drawString(transferDetailsProvider.getCurrency(), xCurrencyAmount - 35, 290);
            g.setColor(Color.decode(black));

            g.drawString(transferDetailsProvider.getAmount(), xCurrencyAmount + 35, 290);
            g.setFont(fontTitle);
            g.drawString(transferDetailsProvider.getDescription(), xDescription, 360);
            g.setColor(Color.decode(gray));
            g.setFont(fontDescription);
            g.drawString(transferDetailsProvider.getDateHour(), xDateHour, 420);
            g.setFont(fontAccountDetail);
            g.drawString(transferDetailsProvider.getFromTitle(), 30, 520);
            g.setColor(Color.decode(black));
            g.drawString(transferDetailsProvider.getFromHolder(), 30, 550);
            g.setColor(Color.decode(gray));
            g.drawString(transferDetailsProvider.getFromAccount(), 30, 580);

            g.drawString(transferDetailsProvider.getToTitle(), 30, 640);
            g.setColor(Color.decode(black));
            g.drawString(transferDetailsProvider.getToHolder(), 30, 670);
            g.setColor(Color.decode(gray));
            g.drawString(transferDetailsProvider.getToAccount(), 30, 700);

            g.drawString(transferDetailsProvider.getNroEntry(), xAccountingEntry, 820);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(background, DEFAULT_IMAGE_FORMAT, outputStream);
            return BASE64_PREFIX + new String(Base64.getEncoder().encode(outputStream.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage decodeBase64Image(String base64Image) throws ProviderException {
        try {
            byte[] imageBytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64Image);
            ByteArrayInputStream bytes = new ByteArrayInputStream(imageBytes);
            return ImageIO.read(bytes);
        } catch (Exception ex) {
            throw new ProviderException(ex.getMessage());
        }
    }
}
