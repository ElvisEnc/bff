package bg.com.bo.service.template.example;

import bg.com.bo.service.template.controller.example.v1.ExampleController;
import bg.com.bo.service.template.model.ErrorResponse;
import bg.com.bo.service.template.model.Example;
import bg.com.bo.service.template.model.ExceptionNotFound;
import bg.com.bo.service.template.model.Response;
import bg.com.bo.service.template.repository.ExampleRepository;
import bg.com.bo.service.template.service.Interfaces.IExampleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ExampleApiTests {
    @InjectMocks
    private ExampleController exampleController;
    @Mock
    private IExampleService exampleService;

    static Example example;

    @BeforeAll
    public static void setup() {
        example = new Example(5,"Run Test");
    }

    // GET
    @Test
    void givenExistingObjectWhenGetExampleThenReturnOKResponse() {
        // Arrange
        int id = 5;
        Mockito.when(exampleService.getExample(id)).thenReturn(example);

        // Act
        ResponseEntity<Example> result = (ResponseEntity<Example>) exampleController.getExample(id);

        // Assert
        assert result.getStatusCode() == HttpStatus.OK;
        assert result.getBody() == example;
        Mockito.verify(exampleService).getExample(id);
        Mockito.verifyNoMoreInteractions(exampleService);
    }

    @Test
    void givenNotExistingObjectWhenGetExampleThenReturnNotFoundResponse() {
        // Arrange
        int id = 1;
        Mockito.when(exampleService.getExample(id)).thenThrow(new ExceptionNotFound("No se encontró el objeto con el id otorgado"));

        // Act
        ResponseEntity<?> response = exampleController.getExample(id);

        // Assert
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
        assert response.getBody() != null;
        Mockito.verify(exampleService).getExample(id);
        Mockito.verifyNoMoreInteractions(exampleService);
    }

    @Test
    void givenInternalErrorWhenGetExampleThenReturnInternalServerError() {
        // Arrange
        int id = 2;
        Mockito.when(exampleService.getExample(id)).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<?> response = exampleController.getExample(id);

        // Assert
        assert response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
        assert response.getBody() != null;
        Mockito.verify(exampleService).getExample(id);
        Mockito.verifyNoMoreInteractions(exampleService);
    }

    // POST
    @Test
    void givenNewExampleWhenPostExampleThenReturnOKResponse() {
        // Arrange
        Response response = new Response();
        Mockito.when(exampleService.createExample(example)).thenReturn(response);

        // Act
        ResponseEntity<Response> result = exampleController.postExample(example);

        // Assert
        assert result.getStatusCode() == HttpStatus.OK;
        assert result.getBody() == response;
        Mockito.verify(exampleService).createExample(example);
        Mockito.verifyNoMoreInteractions(exampleService);
    }

    // PUT
    @Test
    void givenExistingExampleWhenPutExampleThenReturnOKResponse() {
        // Arrange
        Response response = new Response();
        Mockito.when(exampleService.updateExample(example)).thenReturn(response);

        // Act
        ResponseEntity<?> result = exampleController.putExample(example);

        // Assert
        assert result.getStatusCode() == HttpStatus.OK;
        assert result.getBody() == response;
        Mockito.verify(exampleService).updateExample(example);
        Mockito.verifyNoMoreInteractions(exampleService);
    }


    @Test
    void givenInternalErrorWhenPutExampleThenReturnInternalServerError() {
        // Arrange
        Mockito.when(exampleService.updateExample(example)).thenThrow(new ExceptionNotFound("No se encontró el objeto con el id otorgado"));

        // Act
        ResponseEntity<?> result = exampleController.putExample(example);

        // Assert
        assert result.getStatusCode() == HttpStatus.NOT_FOUND;
        assert result.getBody() != null;
        Mockito.verify(exampleService).updateExample(example);
        Mockito.verifyNoMoreInteractions(exampleService);
    }

    @Test
    void givenNotExistingObjectExampleWhenPutExampleThenReturnNotFoundResponse() {
        // Arrange
        Mockito.when(exampleService.updateExample(example)).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<?> result = exampleController.putExample(example);

        // Assert
        assert result.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
        assert result.getBody() != null;
        Mockito.verify(exampleService).updateExample(example);
        Mockito.verifyNoMoreInteractions(exampleService);
    }

    // DELETE
    @Test
    void givenExistingExampleWhenDeleteExampleThenReturnOKResponse() {
        // Arrange
        String id = "1";
        Response response = new Response();
        Mockito.when(exampleService.deleteExample(id)).thenReturn(response);

        // Act
        ResponseEntity<?> result = exampleController.deletExample(id);

        // Assert
        assert result.getStatusCode() == HttpStatus.OK;
        assert result.getBody() == response;
        Mockito.verify(exampleService).deleteExample(id);
        Mockito.verifyNoMoreInteractions(exampleService);
    }
}
