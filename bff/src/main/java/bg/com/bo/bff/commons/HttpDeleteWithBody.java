package bg.com.bo.bff.commons;

import org.apache.http.annotation.Contract;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

@Contract()
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";

    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public HttpDeleteWithBody(final URI uri) {
        super();
        setURI(uri);
    }

    public HttpDeleteWithBody() {
        super();
    }
}
