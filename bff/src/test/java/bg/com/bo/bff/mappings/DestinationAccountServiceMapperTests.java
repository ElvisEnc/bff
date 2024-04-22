package bg.com.bo.bff.mappings;

import bg.com.bo.bff.application.dtos.response.AccountTypeResponse;
import bg.com.bo.bff.commons.enums.AccountType;
import bg.com.bo.bff.mappings.services.DestinationAccountServiceMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DestinationAccountServiceMapperTests {
    private final DestinationAccountServiceMapper mapper = DestinationAccountServiceMapper.INSTANCE;

    @Test
    void givenAccountTypeWhenConvertToAccountTypeResponseThenReturnValidAccountType() {
        //Arrange
        AccountType accountType = AccountType.MOBILE;

        //Act
        AccountTypeResponse accountTypeResponse = mapper.convert(accountType);

        //Assert
        assertEquals(accountType.getCode(), accountTypeResponse.getCode());
        assertEquals(accountType.getDescription(), accountTypeResponse.getDescription());
    }
}
