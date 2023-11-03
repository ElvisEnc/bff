package bg.com.bo.service.template.service.Interfaces;

import bg.com.bo.service.template.model.Example;
import bg.com.bo.service.template.model.Response;

public interface IExampleService {
    Example getExample(int id);

    Response createExample(Example example);

    Response deleteExample(String id);

    Response updateExample(Example example);
}
