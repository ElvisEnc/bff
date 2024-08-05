package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.services.interfaces.IAccountStatementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AccountStatementControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private AccountStatementController controller;

    @Mock
    private IAccountStatementService service;

    private static final String URL_ACCOUNT_STATEMENT = "/api/v1/account-statement/234234/persons/234234";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

}
