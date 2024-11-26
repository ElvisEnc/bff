package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;
import bg.com.bo.bff.providers.interfaces.IGenerateImage;
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
        try (InputStream file = getClass().getResourceAsStream("/layout_comprobante.txt")) {
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
    public String generateImage(TransferMWResponse response) {
        BufferedImage background = decodeBase64Image(layoutBG);
        Graphics2D g = (Graphics2D) background.getGraphics();

        Font fontTitle = new Font("Arial", Font.BOLD, 32);
        Font fontDescription = new Font("Arial", Font.BOLD, 22);
        String black = "#000000";

        g.setColor(Color.decode(black));
        g.setFont(fontTitle);
        g.getFontMetrics(fontTitle);
        g.getFontMetrics(fontDescription);

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
