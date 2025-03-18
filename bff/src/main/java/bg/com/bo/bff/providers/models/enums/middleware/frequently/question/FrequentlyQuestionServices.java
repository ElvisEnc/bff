package bg.com.bo.bff.providers.models.enums.middleware.frequently.question;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FrequentlyQuestionServices {
    GET_FREQUENTLY_QUESTION("/bs/v1/frequently-questions");

    private final String serviceURL;
}
