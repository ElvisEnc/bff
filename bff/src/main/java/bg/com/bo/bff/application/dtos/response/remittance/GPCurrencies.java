package bg.com.bo.bff.application.dtos.response.remittance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GPCurrencies {

        private int currencyId;
        private String abbreviation;
        private BigDecimal purchaseRate;
        private String description;
        private char currencyType;
}
