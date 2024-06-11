package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.request.UpdateDataUserRequest;
import bg.com.bo.bff.application.dtos.response.BiometricsResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.apiface.DistrictsResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdateDataUserResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.apiface.DepartmentsResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.MaritalStatusResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;

import java.io.IOException;
import java.util.Map;

public interface IUserService {
    GenericResponse changePassword(String personId, String personRoleId, ChangePasswordRequest changePasswordRequest, Map<String, String> parameters) throws IOException;

    BiometricsResponse getBiometrics(Integer personId, Map<String, String> parameter) throws IOException;

    UpdateBiometricsResponse updateBiometrics(Integer personId, UpdateBiometricsRequest request, Map<String, String> parameter) throws IOException;

    ContactResponse getContactInfo();

    PersonalResponse getPersonalInformation(String personId, Map<String, String> parameter) throws IOException;

    EconomicActivityResponse getEconomicActivity(Integer personId, Map<String, String> parameter);

    DepartmentsResponse getDepartments(Map<String, String> parameter) throws IOException;

    DistrictsResponse getDistricts(String departmentId, Map<String, String> parameter) throws IOException;

    MaritalStatusResponse getMaritalStatus(Map<String, String> parameter);

    UpdateDataUserResponse updateDataUser(String personId, UpdateDataUserRequest request, Map<String, String> parameter) throws IOException;
}
