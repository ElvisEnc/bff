package bg.com.bo.bff.mappings;

import bg.com.bo.bff.application.dtos.response.destination.account.AccountType;
import bg.com.bo.bff.mappings.services.DestinationAccountServiceMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DestinationOwnAccountServiceMapperTests {
    private final DestinationAccountServiceMapper mapper = DestinationAccountServiceMapper.INSTANCE;

    @Test
    void givenAccountTypeWhenConvertToAccountTypeResponseThenReturnValidAccountType() {
        //Arrange
        bg.com.bo.bff.commons.enums.destination.account.AccountType accountType = bg.com.bo.bff.commons.enums.destination.account.AccountType.MOBILE;

        //Act
        AccountType accountTypeResponse = mapper.convert(accountType);

        //Assert
        assertEquals(accountType.getCode(), accountTypeResponse.getCode());
        assertEquals(accountType.getDescription(), accountTypeResponse.getDescription());
    }
}
