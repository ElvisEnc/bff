package bg.com.bo.bff.providers.dtos.response.apiface;

import java.util.Arrays;

public class DepartmentsNetResponseFixture {
    public static DepartmentsNetResponse withDefault() {
        return DepartmentsNetResponse.builder()
                .data(Arrays.asList(DepartmentNetDetail.builder()
                                .codeError("0")
                                .descriptionError("OK")
                                .codeResult(0)
                                .description("OK")
                                .messageApp("OK")
                                .messageUser("OK")
                                .sessionNumber("0")
                                .codeSquare(0)
                                .build(),
                        DepartmentNetDetail.builder()
                                .codeError("0")
                                .descriptionError("OK")
                                .codeResult(0)
                                .description("OK")
                                .messageApp("OK")
                                .messageUser("OK")
                                .sessionNumber("0")
                                .codeSquare(0)
                                .build())
                )
                .build();
    }
}