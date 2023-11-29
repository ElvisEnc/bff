package bg.com.bo.service.template.repository;

import bg.com.bo.service.template.model.Example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
public class ExampleRepositoryTest {
    private ExampleRepository exampleRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        exampleRepository = new ExampleRepository();
        exampleRepository.setJdbcTemplate(jdbcTemplate);
    }

    @Test
    void givenNewExampleWhenInsertExampleThenReturnIntResult() {
        // Arrange
        Example example = new Example(10, "Test Insert");

        // Act
        int result = exampleRepository.insertExample(example);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void givenExistingObjectWhenGetExampleByIdThenReturnExample() {
        // Arrange
        Integer id = 1;

        // Act
        Example result = exampleRepository.getExampleById(id);

        // Assert
        assertEquals(id, result.getId());
    }

    @Test
    void givenExampleToUpdateWhenUpdateExampleThenReturnIntResult() {
        // Arrange
        Example example = new Example(2, "Update Test");

        // Act
        int result = exampleRepository.updateExample(example);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void givenExampleToDeleteWhenDeleteExampleThenReturnIntResult() {
        // Arrange
        int id = 2;

        // Act
        int result = exampleRepository.deleteExample(id);

        // Assert
        assertEquals(1, result);
    }
}
