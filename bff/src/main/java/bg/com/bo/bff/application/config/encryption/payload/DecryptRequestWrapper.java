package bg.com.bo.bff.application.config.encryption.payload;

import bg.com.bo.bff.models.payload.encryption.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import javax.crypto.*;
import java.io.*;
import java.security.*;

public class DecryptRequestWrapper extends HttpServletRequestWrapper {
    private final String body;

    private final IFirstLayerEncryptionHandler firstLayerEncryptionHandler;
    private EncryptionPayload encryptionPayloadDecrypted;
    private final String payloadAlgorithm;

    public DecryptRequestWrapper(HttpServletRequest request, String payloadAlgorithm, IFirstLayerEncryptionHandler firstLayerEncryptionHandler) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        super(request);
        this.payloadAlgorithm = payloadAlgorithm;
        this.firstLayerEncryptionHandler = firstLayerEncryptionHandler;
        this.processPayload(request);
        this.body = this.encryptionPayloadDecrypted.getBody();
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Util.getEncodedBytes(body));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    private void processPayload(HttpServletRequest request) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String payloadKeys = firstLayerEncryptionHandler.decrypt();

        EncryptionPayload encryptedPayload = getRawPayload(request);

        this.encryptionPayloadDecrypted = AesPayloadResolver.instance(payloadAlgorithm).decrypt(payloadKeys, encryptedPayload);
    }

    /**
     * Obtiene el payload del request en el formato de la clase {@link EncryptionPayload EncryptionPayload} con los datos raw.
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static EncryptionPayload getRawPayload(HttpServletRequest request) throws IOException {
        String rawPayload = Util.getPayload(request);
        if (rawPayload.isEmpty())
            return null;
        return Util.stringToObject(rawPayload, EncryptionPayload.class);
    }
}
