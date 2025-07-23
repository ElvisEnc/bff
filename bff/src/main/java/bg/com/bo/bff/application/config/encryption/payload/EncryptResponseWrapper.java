package bg.com.bo.bff.application.config.encryption.payload;

import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.enums.config.provider.EncryptionHeaders;
import bg.com.bo.bff.models.payload.encryption.IFirstLayerEncryptionHandler;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.payload.encryption.AesPayloadResolver;
import bg.com.bo.bff.models.payload.encryption.EncryptionPayload;
import bg.com.bo.bff.models.payload.encryption.EncryptionPayloadResult;
import bg.com.bo.bff.models.payload.encryption.PayloadKey;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.*;
import java.util.Base64;

public class EncryptResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream capture;
    private ServletOutputStream output;
    private PrintWriter writer;

    @lombok.Getter
    private String content;
    private final IFirstLayerEncryptionHandler firstLayerEncryptionHandler;
    private SecretKey secretKey;
    private IvParameterSpec iv;
    private final String payloadAlgorithm;

    public EncryptResponseWrapper(HttpServletResponse response, IFirstLayerEncryptionHandler firstLayerEncryptionHandler, String payloadAlgorithm) {
        super(response);
        this.firstLayerEncryptionHandler = firstLayerEncryptionHandler;
        this.capture = new ByteArrayOutputStream(response.getBufferSize());
        this.payloadAlgorithm = payloadAlgorithm;
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (output == null) {
            output = new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    capture.write(b);
                }

                @Override
                public void flush() throws IOException {
                    capture.flush();
                }

                @Override
                public void close() throws IOException {
                    capture.close();
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener arg0) {
                }
            };
        }

        return output;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (output != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            writer = new PrintWriter(new OutputStreamWriter(capture,
                    getCharacterEncoding()));
        }

        return writer;
    }

    @Override
    public void flushBuffer() {
        try {
            wrapContent();
            setEncryptionHeadersData();

            super.flushBuffer();

            if (writer != null) {
                writer.flush();
            } else if (output != null) {
                output.flush();
            }
        } catch (Exception e) {
            throw new HandledException();
        }
    }

    private byte[] getCaptureAsBytes() throws IOException {
        if (writer != null) {
            writer.close();
        } else if (output != null) {
            output.close();
        }

        return capture.toByteArray();
    }

    /**
     * Setea la clave de encriptacion AES del payload, encriptada a su vez con RSA. Modifica tambien el content type y el content length resultante de la encriptacion del body original.
     */
    private void setEncryptionHeadersData() {
        this.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        this.setHeader("Content-Length", String.valueOf(Util.getEncodedBytes(content).length));
        this.setHeader(EncryptionHeaders.SESSION_ENCRYPT_KEY_HEADER.getCode(), this.encryptKeyHeader());
    }

    /**
     * Envuelve la respuesta en el formato de {@link EncryptionPayload EncryptionPayload} con el body encriptado.
     *
     * @throws IOException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     */
    private void wrapContent() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String contentType = this.getContentType();

        String body = new String(getCaptureAsBytes(), getCharacterEncoding());

        EncryptionPayloadResult encryptionPayload = AesPayloadResolver.instance(payloadAlgorithm).encrypt(body, contentType);
        this.secretKey = encryptionPayload.getSecretKey();
        this.iv = encryptionPayload.getIv();

        content = Util.objectToString(encryptionPayload.getData());
    }

    /**
     * Encripta con RSA los datos necesarios para la desencriptacion del payload.
     *
     * @return
     */
    private String encryptKeyHeader() {
        PayloadKey payloadKey = PayloadKey.builder()
                .secret(Base64.getEncoder().encodeToString(secretKey.getEncoded()))
                .iv(Base64.getEncoder().encodeToString(iv.getIV()))
                .build();

        return firstLayerEncryptionHandler.encrypt(payloadKey);
    }
}