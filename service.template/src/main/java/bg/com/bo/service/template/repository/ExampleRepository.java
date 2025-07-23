package bg.com.bo.service.template.repository;

import bg.com.bo.service.template.model.Example;
import bg.com.bo.service.template.repository.Interfaces.IExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExampleRepository implements IExampleRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertExample(Example example) {
        return jdbcTemplate.update("INSERT INTO example (id, description) VALUES (?, ?)", example.getId(), example.getDescription());
    }

    public Example getExampleById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM example WHERE id = ?", new Object[]{id}, (resultSet, rowNum) ->
                new Example(resultSet.getInt("id"), resultSet.getString("description")));
    }

    public int updateExample(Example example) {
        return jdbcTemplate.update("UPDATE example SET description = ? WHERE id = ?", example.getDescription(), example.getId());
    }

    public int deleteExample(Integer id) {
        return jdbcTemplate.update("DELETE FROM example WHERE id = ?", id);
    }
}
