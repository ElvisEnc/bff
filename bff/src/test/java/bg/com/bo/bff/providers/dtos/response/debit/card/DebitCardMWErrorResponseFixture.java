package bg.com.bo.bff.providers.dtos.response.debit.card;

import bg.com.bo.bff.providers.dtos.response.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.ErrorDetailResponse;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareError;

import java.util.ArrayList;
import java.util.List;

public class DebitCardMWErrorResponseFixture {
    public static ApiErrorResponse withDefault() {
        List<ErrorDetailResponse> list = new ArrayList<>();
        ErrorDetailResponse errorDetailResponse = ErrorDetailResponse.builder()
                .code(DebitCardMiddlewareError.MDWTJD_002.getCodeMiddleware())
                .description(DebitCardMiddlewareError.MDWTJD_002.getMessage())
                .build();

        list.add(errorDetailResponse);

        return ApiErrorResponse.builder()
                .errorDetailResponse(list)
                .build();
    }
}