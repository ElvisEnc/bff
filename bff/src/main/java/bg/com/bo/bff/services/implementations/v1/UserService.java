package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateDataUserRequest;
import bg.com.bo.bff.application.dtos.response.user.BiometricsResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.apiface.DistrictsResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdateDataUserResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.user.apiface.DepartmentsResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.MaritalStatusResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdatePersonalDetail;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.Gender;
import bg.com.bo.bff.commons.enums.MaritalStatus;
import bg.com.bo.bff.commons.validators.generics.*;
import bg.com.bo.bff.providers.dtos.request.personal.information.ApiPersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.DistrictsNetRequest;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdatePersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.login.mw.BiometricStatusMWResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.EconomyActivity;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import bg.com.bo.bff.providers.interfaces.IApiFaceNetProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.IPersonalInformationNetProvider;
import bg.com.bo.bff.mappings.providers.apiface.IApiFaceMapper;
import bg.com.bo.bff.mappings.providers.information.IPersonalInformationMapper;
import bg.com.bo.bff.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private static final String HAS_HUSBAND_LAST_NAME_YES = "S";
    private static final String HAS_HUSBAND_LAST_NAME_NO = "N";
    private static final String MARRIED_AND_COMMON_LAW_UNION = "CU";
    private static final String BANK_EMPLOYEE = "3";
    private static final String DEPENDENT_INCOME_SOURCE ="D";
    private final ILoginMiddlewareProvider loginMiddlewareProvider;
    private final IPersonalInformationNetProvider personalInformationNetProvider;
    private final IPersonalInformationMapper iPersonalInformationMapper;
    private final IApiFaceNetProvider apiFaceNetProvider;
    private final IApiFaceMapper iApiFaceMapper;

    @Autowired
    public UserService(ILoginMiddlewareProvider provider, IPersonalInformationNetProvider providerPersonal, IPersonalInformationMapper iPersonalInformationMapper, IApiFaceNetProvider apiFaceNetProvider,
                       IApiFaceMapper iApiFaceMapper) {
        this.loginMiddlewareProvider = provider;
        this.personalInformationNetProvider = providerPersonal;
        this.iPersonalInformationMapper = iPersonalInformationMapper;
        this.apiFaceNetProvider = apiFaceNetProvider;
        this.iApiFaceMapper = iApiFaceMapper;
    }

    @Override
    public GenericResponse changePassword(String personId, String personRoleId, ChangePasswordRequest changePasswordRequest, Map<String, String> parameters) throws IOException {
        IValidator<String> validator = new NotEqualsValidator<>(changePasswordRequest.getOldPassword(), new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.SAME_PASSWORD));
        validator.setNext(new MinLengthValidator(Constants.PASSWORD_MIN_LENGTH, new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)))
                .setNext(new MaxLengthValidator(Constants.PASSWORD_MAX_LENGTH, new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)))
                .setNext(new ContainsDigitValidator(new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)))
                .setNext(new ContainsLetterValidator(new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)));
        validator.validate(changePasswordRequest.getNewPassword());

        return loginMiddlewareProvider.changePassword(personId, personRoleId, changePasswordRequest, parameters);
    }

    @Override
    public BiometricsResponse getBiometrics(Integer personId, Map<String, String> parameter) throws IOException {
        BiometricStatusMWResponse mwResponse = loginMiddlewareProvider.getBiometricsMW(personId, parameter);
        return BiometricsResponse.builder()
                .status(Objects.equals(mwResponse.getData().getStatusBiometric(), "S"))
                .authenticationType(mwResponse.getData().getAuthenticationType())
                .build();
    }

    @Override
    public UpdateBiometricsResponse updateBiometrics(Integer personId, UpdateBiometricsRequest request, Map<String, String> parameter) throws IOException {
        if (request.getStatus() == null) {
            throw new GenericException("status no puede estar vacío", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
        }
        return loginMiddlewareProvider.updateBiometricsMW(personId, request, parameter);
    }

    @Override
    public ContactResponse getContactInfo() {
        return loginMiddlewareProvider.getContactInfo();
    }

    @Override
    public PersonalResponse getPersonalInformation(String personId, Map<String, String> parameters) throws IOException {
        ApiPersonalInformationNetRequest requestData = iPersonalInformationMapper.mapperRequest(personId);
        PersonalInformationNetResponse response = personalInformationNetProvider.getPersonalInformation(requestData, parameters);
        return iPersonalInformationMapper.convertRequest(response);
    }

    @Override
    public EconomicActivityResponse getEconomicActivity(Integer personId, Map<String, String> parameter) {
        return personalInformationNetProvider.getEconomicalActivity(personId);
    }

    @Override
    public DepartmentsResponse getDepartments(Map<String, String> parameter) throws IOException {
        DepartmentsNetResponse netResponse = apiFaceNetProvider.getDepartments(parameter);
        return iApiFaceMapper.mapToDepartmentsResponse(netResponse);
    }

    @Override
    public DistrictsResponse getDistricts(String departmentId, Map<String, String> parameter) throws IOException {
        DistrictsNetRequest requestData = iPersonalInformationMapper.mapToDistrictRequest(String.valueOf(departmentId));
        DistrictsNetResponse netResponse = personalInformationNetProvider.getDistricts(requestData, parameter);

        return iPersonalInformationMapper.mapToDistrictsResponse(netResponse);
    }

    @Override
    public MaritalStatusResponse getMaritalStatus(Map<String, String> parameter) {
        return personalInformationNetProvider.getMaritalStatuses();
    }

    @Override
    public GenericResponse updateDataUser(String personId, UpdateDataUserRequest request, Map<String, String> parameter) throws IOException {

        ApiPersonalInformationNetRequest requestData = iPersonalInformationMapper.mapperRequest(personId);

        PersonalInformationNetResponse personalInformation = personalInformationNetProvider.getPersonalInformation(requestData, parameter);

        if (personalInformation.getDataContent().getClientDataList().isEmpty()) {
            AppError error = AppError.NOT_ACCEPTABLE_UPDATE_PERSONAL_INFORMATION;
            throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
        }

        request.setReference(validateReference(request.getReference(), personalInformation));

        request.setMaritalStatus(validateMaritalStatus(request.getMaritalStatus(),
                personalInformation.getDataContent().getClientDataList().get(0).getGender()));

        validateCityCode(request.getPersonalData().getDepartmentCode(),
                request.getPersonalData().getCityCode(), parameter);

        UpdatePersonalDetail.EconomicalActivity economicalActivity   = validateEconomicalActivity(
                personId,
                request.getEconomicalActivity(),
                request.getPersonalData().getBankEmployee(),
                personalInformation.getDataContent().getClientDataList().get(0).getIncomeLevel(),
                personalInformation.getDataContent().getClientDataList().get(0).getEconomicActivity(),
                personalInformation.getDataContent().getEconomicActivities().get(0)
        );

        request.setEconomicalActivity(economicalActivity);
        UpdatePersonalInformationNetRequest updatePersonalInformationNetRequest = iPersonalInformationMapper.convertRequest(personId, request, personalInformation);
        personalInformationNetProvider.updatePersonalInformation(updatePersonalInformationNetRequest, parameter);

        return GenericResponse.instance(UpdateDataUserResponse.SUCCESS);
    }

    private UpdatePersonalDetail.EconomicalActivity validateEconomicalActivity(String personId,
                                                                               UpdatePersonalDetail.EconomicalActivity economicalActivity,
                                                                               String bankEmployee,
                                                                               String incomeLevel,
                                                                               int economicActivity,
                                                                               EconomyActivity economyActivity) {
        if(bankEmployee.equals(BANK_EMPLOYEE)){
            return UpdatePersonalDetail.EconomicalActivity.builder()
                    .type(economyActivity.getIncomeSource())
                    .company(economyActivity.getCompany())
                    .position(economyActivity.getPosition())
                    .incomeLevel(Integer.valueOf(incomeLevel))
                    .economicActivity(economicActivity)
                    .build();
        }

        final EconomicActivityResponse economicActivityResponse = personalInformationNetProvider.getEconomicalActivity(Integer.valueOf(personId));

        if(economicActivityResponse.getIncomeSource().stream().noneMatch(x->x.getId().equals(economicalActivity.getType()))) {
            throw new GenericException(AppError.INCOME_SOURCE_NOT_EXIST.getMessage(), AppError.INCOME_SOURCE_NOT_EXIST.getHttpCode(), AppError.INCOME_SOURCE_NOT_EXIST.getCode());
        }

        if(economicActivityResponse.getIncomeLevel().stream().noneMatch(x->x.getId().equals(economicalActivity.getIncomeLevel().toString()))){
            throw new GenericException(AppError.INCOME_LEVEL_NOT_EXIST.getMessage(), AppError.INCOME_LEVEL_NOT_EXIST.getHttpCode(), AppError.INCOME_LEVEL_NOT_EXIST.getCode());
        }

        if(!economicalActivity.getType().equals(DEPENDENT_INCOME_SOURCE)){
            return economicalActivity;
        }

        if(economicActivityResponse.getJobTitle().stream().noneMatch(x->x.getId().equals(economicalActivity.getPosition()))){
            throw new GenericException(AppError.POSITION_NOT_EXIST.getMessage(), AppError.POSITION_NOT_EXIST.getHttpCode(), AppError.POSITION_NOT_EXIST.getCode());
        }

        if(economicActivityResponse.getEconomicActivity().stream().noneMatch(x->x.getId().equals(String.valueOf(economicalActivity.getEconomicActivity())))){
            throw new GenericException(AppError.ECONOMIC_ACTIVITY_NOT_EXIST.getMessage(), AppError.ECONOMIC_ACTIVITY_NOT_EXIST.getHttpCode(), AppError.ECONOMIC_ACTIVITY_NOT_EXIST.getCode());
        }

        if(economicalActivity.getCompany() ==  null){
            throw new GenericException(AppError.COMPANY_NAME_NOT_NULL.getMessage(), AppError.COMPANY_NAME_NOT_NULL.getHttpCode(), AppError.COMPANY_NAME_NOT_NULL.getCode());
        }
        return economicalActivity;
    }

    private UpdatePersonalDetail.Reference validateReference(UpdatePersonalDetail.Reference reference, PersonalInformationNetResponse personalInformation) {
        if (reference == null) {
            return UpdatePersonalDetail.Reference.builder()
                    .name(personalInformation.getDataContent().getReferences().get(0).getName())
                    .telephone(personalInformation.getDataContent().getReferences().get(0).getPhone())
                    .ordinal(personalInformation.getDataContent().getReferences().get(0).getOrdinal())
                    .relationship(personalInformation.getDataContent().getReferences().get(0).getRelation())
                    .build();
        }
        if (reference.getName() == null || reference.getTelephone() == null) {
            throw new GenericException(AppError.REFERENCE_INVALID.getMessage(), AppError.REFERENCE_INVALID.getHttpCode(), AppError.REFERENCE_INVALID.getCode());
        }
        if (reference.getName().isBlank()) {
            throw new GenericException(AppError.REFERENCE_INVALID.getMessage(), AppError.REFERENCE_INVALID.getHttpCode(), AppError.REFERENCE_INVALID.getCode());

        }

        if (reference.getTelephone().isBlank()) {
            throw new GenericException(AppError.REFERENCE_INVALID.getMessage(), AppError.REFERENCE_INVALID.getHttpCode(), AppError.REFERENCE_INVALID.getCode());
        }
        return reference;

    }

    private void validateCityCode(int departmentId, Integer cityCode, Map<String, String> parameter) throws IOException {


        DistrictsNetRequest requestData = iPersonalInformationMapper.mapToDistrictRequest(String.valueOf(departmentId));
        DistrictsNetResponse netResponse = personalInformationNetProvider.getDistricts(requestData, parameter);

        if (netResponse.getResult().getData().stream().map(x->Integer.valueOf(x.getCodeDistrict())).anyMatch(data -> data.equals(cityCode))) {
            return;
        }

        AppError error = AppError.CITY_CODE_NOT_EXIST;
        throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
    }

    private UpdatePersonalDetail.MaritalStatus validateMaritalStatus(UpdatePersonalDetail.MaritalStatus maritalStatus, String gender) {

        if (maritalStatus.getHasHusbandLastName() == null) {
            maritalStatus.setHasHusbandLastName(HAS_HUSBAND_LAST_NAME_NO);
        }
        if (!MARRIED_AND_COMMON_LAW_UNION.contains(maritalStatus.getStatus())) {
           return maritalStatus;
        }

        if (Optional.ofNullable(maritalStatus.getSpouseName()).isEmpty()) {
            AppError error = AppError.VALIDATE_MARRIED_PERSON;
            throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
        }

        if(maritalStatus.getStatus().equals(MaritalStatus.COMMON_LAW_UNION.getCode())){
            maritalStatus.setStatus(HAS_HUSBAND_LAST_NAME_NO);
            return maritalStatus;
        }

        if (!gender.equals(Gender.FEMALE.getCode())) {
            return maritalStatus;
        }

        if (maritalStatus.getHasHusbandLastName().equals(HAS_HUSBAND_LAST_NAME_NO)) {
            return maritalStatus;
        }

        if (Optional.ofNullable(maritalStatus.getHusbandLastName()).orElse("").isBlank()) {
            AppError error = AppError.VALIDATE_MARRIED_AND_USE_HUSBAND_LAST_NAME_PERSON;
            throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
        }
        return maritalStatus;
    }
}
