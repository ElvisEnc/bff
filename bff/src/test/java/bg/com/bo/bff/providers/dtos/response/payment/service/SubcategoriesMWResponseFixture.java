package bg.com.bo.bff.providers.dtos.response.payment.service;

import java.util.List;

public class SubcategoriesMWResponseFixture {

    public static SubcategoriesMWResponse withDefault(){
        return new SubcategoriesMWResponse(
                List.of(
                        SubcategoriesMWResponse.SubcategoryMW.builder()
                                .subCategoryCod(5)
                                .categoryCod(2)
                                .description("UNIVERSIDADES")
                                .hasCity("N")
                                .build(),
                        SubcategoriesMWResponse.SubcategoryMW.builder()
                                .subCategoryCod(4)
                                .categoryCod(2)
                                .description("COLEGIOS")
                                .hasCity("S")
                                .build(),
                        SubcategoriesMWResponse.SubcategoryMW.builder()
                                .subCategoryCod(6)
                                .categoryCod(2)
                                .description("INSTITUTOS")
                                .hasCity("N")
                                .build()
                )
        );
    }

    public static String errorMDWPSM_003(){
        return "{\"code\":406,\"errorType\":\"Technical\",\"errorDetailResponse\":[{\"code\":\"MDWPSM-003\",\"description\":\"Not found sub categories\"}]}";
    }
}