package bg.com.bo.bff.application.dtos.response.payment.services;

import java.util.List;

public class SubCategoryCitiesResponseFixture {
    public static SubCategoryCitiesResponse withDefault(){
        List<SubCategoryCitiesResponse.City> data = List.of(
                new SubCategoryCitiesResponse.City(1, "COCHABAMBA"),
                new SubCategoryCitiesResponse.City(1, "SANTA CRUZ")
        );
        return SubCategoryCitiesResponse.builder()
                .data(data)
                .build();
    }
}
