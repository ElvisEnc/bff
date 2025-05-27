package bg.com.bo.bff.application.dtos.response.payment.service;

import java.util.Arrays;
import java.util.List;

public class PaymentTypeResponseFixture {

    public static List<PaymentTypeResponse> withDefaults(){
        return Arrays.asList(
                PaymentTypeResponse.builder()
                        .concept("1")
                        .description("Concept 1")
                        .abbreviation("C1")
                        .build(),
                PaymentTypeResponse.builder()
                        .concept("2")
                        .description("Concept 2")
                        .abbreviation("C2")
                        .build(),
                PaymentTypeResponse.builder()
                        .concept("3")
                        .description("Concept 3")
                        .abbreviation("C3")
                        .build()
        );
    }

}
