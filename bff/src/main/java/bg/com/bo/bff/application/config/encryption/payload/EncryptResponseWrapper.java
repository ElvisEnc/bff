package bg.com.bo.bff.application.config.encryption.payload;

import bg.com.bo.bff.commons.enums.EncryptionAlgorithm;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.EncodeInfo;
import bg.com.bo.bff.models.PayloadKey;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.http.HttpServletResponse;

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
    private final EncodeInfo encodeInfo;
    private final IEncryptionService encryptionService;
    private SecretKey secretKey;
    private IvParameterSpec iv;

    private PayloadKey payloadKey;

    public EncryptResponseWrapper(HttpServletResponse response, IEncryptionService encryptionService, EncodeInfo encodeInfo) throws NoSuchAlgorithmException {
        super(response);
        this.encodeInfo = encodeInfo;
        this.encryptionService = encryptionService;
        this.capture = new ByteArrayOutputStream(response.getBufferSize());

        this.secretKey = CipherUtils.generateKey(EncryptionAlgorithm.AES_256_CBC_PKCS5_PADDING);
        this.iv = CipherUtils.generateIv();

        this.payloadKey = PayloadKey.builder()
                .secret(Base64.getEncoder().encodeToString(secretKey.getEncoded()))
                .iv(Base64.getEncoder().encodeToString(iv.getIV()))
                .build();
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
    public void flushBuffer() throws IOException {
        super.flushBuffer();

        if (writer != null) {
            writer.flush();
        } else if (output != null) {
            output.flush();
        }
    }

    public byte[] getCaptureAsBytes() throws IOException {
        if (writer != null) {
            writer.close();
        } else if (output != null) {
            output.close();
        }

        return capture.toByteArray();
    }

    public String encrypt() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String body = new String(getCaptureAsBytes(), getCharacterEncoding());
        return CipherUtils.encrypt(EncryptionAlgorithm.AES_256_CBC_PKCS5_PADDING, body, secretKey, iv);
    }

    public String getPayloadEncrypted() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PublicKey publicKey = this.encryptionService.getUserPublicKey(encodeInfo);
        return CipherUtils.encrypt(EncryptionAlgorithm.RSA, Util.objectToString(payloadKey), publicKey);
    }
}