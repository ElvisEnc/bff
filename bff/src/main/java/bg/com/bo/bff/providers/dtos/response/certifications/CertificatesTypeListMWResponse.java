package bg.com.bo.bff.providers.dtos.response.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificatesTypeListMWResponse {
    private List<CertificateTypeMDW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CertificateTypeMDW{
        private int requestCode;
        private int typeCode;
        private String description;
    }

}
