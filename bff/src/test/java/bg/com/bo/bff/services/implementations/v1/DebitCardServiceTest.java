package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequest;
import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsRequestFixture;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.providers.dtos.request.debit.card.DCLimitsMWRequest;
import bg.com.bo.bff.providers.dtos.request.debit.card.DebitCardMWRequestFixture;
import bg.com.bo.bff.providers.interfaces.IDebitCardProvider;
import bg.com.bo.bff.providers.mappings.debit.card.IDebitCardMapper;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DebitCardServiceTest {
    @Spy
    @InjectMocks
    private DebitCardService service;

    @Mock
    private IDebitCardProvider provider;

    @Mock
    private IDebitCardMapper mapper;

    @Test
    void givenValidDataWhenChangeAmountThenReturnSuccess() throws IOException {
        // Arrange
        String personId = "169494";
        String cardId = "169494";
        DCLimitsRequest request = DCLimitsRequestFixture.withDefault();
        DCLimitsMWRequest expectedRequest = DebitCardMWRequestFixture.withDefault();
        GenericResponse expected = GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT);

        Mockito.when(provider.changeAmount(Mockito.any(), Mockito.any())).thenReturn(GenericResponse.instance(DebitCardMiddlewareResponse.SUCCESS_CHANGE_AMOUNT));
        Mockito.when(mapper.mapToLimitsRequest(request, personId, cardId)).thenReturn(expectedRequest);

        // Act
        GenericResponse response = service.changeAmount(personId, cardId, request, new HashMap<>());

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expected, response);
        verify(provider).changeAmount(expectedRequest, new HashMap<>());
        verify(mapper).mapToLimitsRequest(request, personId, cardId);
    }
}