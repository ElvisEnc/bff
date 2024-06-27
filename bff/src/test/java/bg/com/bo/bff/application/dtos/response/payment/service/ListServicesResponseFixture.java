package bg.com.bo.bff.application.dtos.response.payment.service;

import java.util.List;

public class ListServicesResponseFixture {
    public static ListServicesResponse withDefault() {
        return new ListServicesResponse(
                List.of(
                        ListServicesResponse.Service.builder()
                                .serviceId("1")
                                .description("Servicio 1")
                                .build(),
                        ListServicesResponse.Service.builder()
                                .serviceId("2")
                                .description("Servicio 2")
                                .build(),
                        ListServicesResponse.Service.builder()
                                .serviceId("3")
                                .description("Servicio 3")
                                .build()
                )
        );
    }
}
