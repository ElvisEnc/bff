package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;

import java.io.IOException;
import java.util.Map;

public interface IApiFaceNetProvider {
    DepartmentsNetResponse getDepartments() throws IOException;
}
