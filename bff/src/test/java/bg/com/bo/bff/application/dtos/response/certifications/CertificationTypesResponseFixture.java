package bg.com.bo.bff.application.dtos.response.certifications;

import java.util.Arrays;
import java.util.List;

public class CertificationTypesResponseFixture {

    public static List<CertificationTypesResponse> withDefaults() {
        return Arrays.asList(
                CertificationTypesResponse.builder()
                        .requestCode(123)
                        .typeCode(321)
                        .description("Description")
                        .build(),
                CertificationTypesResponse.builder()
                        .requestCode(123)
                        .typeCode(321)
                        .description("Description")
                        .build()
                );
    }
}
