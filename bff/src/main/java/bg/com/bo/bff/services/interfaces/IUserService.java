package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateDataUserRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.BiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.MaritalStatusResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.apiface.DepartmentsResponse;
import bg.com.bo.bff.application.dtos.response.user.apiface.DistrictsResponse;

import java.io.IOException;

public interface IUserService {
    GenericResponse changePassword(String personId, String personRoleId, ChangePasswordRequest changePasswordRequest)
            throws IOException;

    BiometricsResponse getBiometrics(Integer personId) throws IOException;

    UpdateBiometricsResponse updateBiometrics(Integer personId, UpdateBiometricsRequest request) throws IOException;

    ContactResponse getContactInfo();

    PersonalResponse getPersonalInformation(String personId) throws IOException;

    EconomicActivityResponse getEconomicActivity(Integer personId);

    DepartmentsResponse getDepartments() throws IOException;

    DistrictsResponse getDistricts(String departmentId) throws IOException;

    MaritalStatusResponse getMaritalStatus();

    GenericResponse updateDataUser(String personId, UpdateDataUserRequest request) throws IOException;
}
