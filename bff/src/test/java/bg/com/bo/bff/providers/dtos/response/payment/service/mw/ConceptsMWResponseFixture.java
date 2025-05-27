package bg.com.bo.bff.providers.dtos.response.payment.service.mw;

import java.util.Arrays;

public class ConceptsMWResponseFixture {

    public static ConceptsMWResponse withDefaults(){
        return ConceptsMWResponse.builder()
                .data(
                        Arrays.asList(
                                ConceptsMWResponse.ConceptItem.builder()
                                        .concept("1")
                                        .description("Concept 1")
                                        .abbreviation("C1")
                                        .build(),
                                ConceptsMWResponse.ConceptItem.builder()
                                        .concept("2")
                                        .description("Concept 2")
                                        .abbreviation("C2")
                                        .build(),
                                ConceptsMWResponse.ConceptItem.builder()
                                        .concept("3")
                                        .description("Concept 3")
                                        .abbreviation("C3")
                                        .build()
                        )
                )
                .build();
    }

    public static ConceptsMWResponse withNullData(){
        return ConceptsMWResponse.builder()
                .data(null)
                .build();
    }

}
