package bg.com.bo.bff.application.config.request.tracing;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
public  abstract class AbstractBFFController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    public void getDeviceDataHeader(){
        HeadersData.buildDeviceData(this.httpServletRequest);
    }
}
