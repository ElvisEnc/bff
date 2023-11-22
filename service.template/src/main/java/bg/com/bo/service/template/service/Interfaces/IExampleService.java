package bg.com.bo.service.template.service.Interfaces;

import bg.com.bo.service.template.model.Example;
import bg.com.bo.service.template.model.Response;

import java.io.IOException;

public interface IExampleService {
    Example getExample(int id) throws IOException;

    Response createExample(Example example) throws IOException;

    Response deleteExample(String id) throws IOException;

    Response updateExample(Example example) throws IOException;
}
