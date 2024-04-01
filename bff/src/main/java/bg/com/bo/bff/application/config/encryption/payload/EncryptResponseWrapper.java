package bg.com.bo.bff.application.config.encryption.payload;

import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.enums.EncryptionAlgorithm;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.EncryptInfo;
import bg.com.bo.bff.models.EncryptionPayload;
import bg.com.bo.bff.models.PayloadKey;
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
    private final IEncryptionService encryptionService;

    private final ByteArrayOutputStream capture;
    private ServletOutputStream output;
    private PrintWriter writer;

    @lombok.Getter
    private String content;
    private final EncryptInfo encodeInfo;
    private SecretKey secretKey;
    private IvParameterSpec iv;

    public EncryptResponseWrapper(HttpServletResponse response, IEncryptionService encryptionService, EncryptInfo encodeInfo) throws NoSuchAlgorithmException {
        super(response);
        this.encodeInfo = encodeInfo;
        this.encryptionService = encryptionService;
        this.capture = new ByteArrayOutputStream(response.getBufferSize());
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
            setPayloadKeys();
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
     * Genera los datos necesarios para la encriptacion del payload.
     *
     * @throws NoSuchAlgorithmException
     */
    private void setPayloadKeys() throws NoSuchAlgorithmException {
        this.secretKey = CipherUtils.generateKey(EncryptionAlgorithm.AES_256_CBC_PKCS5_PADDING);
        this.iv = CipherUtils.generateIv();
    }

    /**
     * Setea la clave de encriptacion AES del payload, encriptada a su vez con RSA. Modifica tambien el content type y el content length resultante de la encriptacion del body original.
     *
     * @throws IOException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     */
    private void setEncryptionHeadersData() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        this.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        this.setHeader("Content-Length", String.valueOf(Util.getEncodedBytes(content).length));
        this.setHeader(Constants.SESSION_ENCRYPTED_KEY_HEADER, this.encryptKeyHeader());
    }

    /**
     * Envuelve la respuesta en el formato de {@link bg.com.bo.bff.models.EncryptionPayload EncryptionPayload} con el body encriptado.
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

        String encryptedBody = CipherUtils.encrypt(EncryptionAlgorithm.AES_256_CBC_PKCS5_PADDING, body, secretKey, iv);

        EncryptionPayload encryptionPayload = EncryptionPayload.builder()
                .body(encryptedBody)
                .contentType(contentType)
                .build();

        content = Util.objectToString(encryptionPayload);
    }

    /**
     * Encripta con RSA los datos necesarios para la desencriptacion del payload.
     *
     * @return
     * @throws IOException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     */
    private String encryptKeyHeader() throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PublicKey publicKey = this.encryptionService.getUserPublicKey(encodeInfo);

        PayloadKey payloadKey = PayloadKey.builder()
                .secret(Base64.getEncoder().encodeToString(secretKey.getEncoded()))
                .iv(Base64.getEncoder().encodeToString(iv.getIV()))
                .build();

        return CipherUtils.encrypt(EncryptionAlgorithm.RSA, Util.objectToString(payloadKey), publicKey);
    }
}