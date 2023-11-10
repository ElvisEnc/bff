package bg.com.bo.service.template.service;

import bg.com.bo.service.template.model.Example;
import bg.com.bo.service.template.model.ExceptionNotFound;
import bg.com.bo.service.template.model.Response;
import bg.com.bo.service.template.service.Interfaces.IExampleService;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class ExampleService implements IExampleService {

    public Example getExample(int id) {
        if (Objects.equals(id, 1)) throw new ExceptionNotFound("No se encontró el objeto con el id otorgado");
        if (Objects.equals(id, 2)) throw new RuntimeException();
        Example example = new Example();
        example.setId(id);
        example.setDescription("Desde el ExampleService, con id: " + id);
        return example;
    }

    public Response createExample(Example example) {
        Response response = new Response();
        response.setStatusCode("SUCCESS");
        response.setMessage("Created: " + example.getId());
        return response;
    }

    public Response updateExample(Example example) {
        if (example.getId() == 0) throw new ExceptionNotFound("No se encontró el objeto con el id otorgado");
        if (example.getId() == -1) throw new RuntimeException();
        Response response = new Response();
        response.setStatusCode("SUCCESS");
        response.setMessage("Actualizado correctamente, con los datos: " + example.getId());
        return response;
    }

    public Response deleteExample(String id) {
        Response response = new Response();
        response.setStatusCode("SUCCESS");
        response.setMessage("Eliminado correctamente, con el id: " + id);
        return response;
    }
}
