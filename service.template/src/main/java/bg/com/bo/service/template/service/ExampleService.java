package bg.com.bo.service.template.service;

import bg.com.bo.service.template.model.Example;
import bg.com.bo.service.template.model.Response;
import bg.com.bo.service.template.service.Interfaces.IExampleService;
import org.springframework.stereotype.Service;


@Service
public class ExampleService implements IExampleService {

    public Example getExample(int id) {
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

    public Response deleteExample(String id) {
        Response response = new Response();
        response.setStatusCode("SUCCESS");
        response.setMessage("Eliminado correctamente, con el id: " + id);
        return response;
    }

    public Response updateExample(Example example) {
        Response response = new Response();
        response.setStatusCode("SUCCESS");
        response.setMessage("Actualizado correctamente, con los datos: " + example.getId());
        return response;
    }
}
