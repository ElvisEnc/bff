package bg.com.bo.service.template.repository.Interfaces;

import bg.com.bo.service.template.model.Example;

public interface IExampleRepository {
    int insertExample(Example example);

    Example getExampleById(Integer id);

    int updateExample(Example example);

    int deleteExample(Integer id);
}
