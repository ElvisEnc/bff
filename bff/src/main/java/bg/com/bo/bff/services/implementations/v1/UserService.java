package bg.com.bo.bff.services.implementations.v1;

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
import bg.com.bo.bff.application.dtos.response.user.UpdateDataUserResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdatePersonalDetail;
import bg.com.bo.bff.application.dtos.response.user.apiface.DepartmentsResponse;
import bg.com.bo.bff.application.dtos.response.user.apiface.DistrictsResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.enums.user.Gender;
import bg.com.bo.bff.commons.enums.user.MaritalStatus;
import bg.com.bo.bff.commons.validators.generics.ContainsDigitValidator;
import bg.com.bo.bff.commons.validators.generics.ContainsLetterValidator;
import bg.com.bo.bff.commons.validators.generics.IValidator;
import bg.com.bo.bff.commons.validators.generics.MaxLengthValidator;
import bg.com.bo.bff.commons.validators.generics.MinLengthValidator;
import bg.com.bo.bff.mappings.providers.apiface.IApiFaceMapper;
import bg.com.bo.bff.mappings.providers.information.IPersonalInformationMapper;
import bg.com.bo.bff.mappings.providers.login.ILoginMapper;
import bg.com.bo.bff.providers.dtos.request.login.mw.ChangePasswordMWRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.UpdateBiometricsMWRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.ApiPersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.DistrictsNetRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.UpdatePersonalInformationNetRequest;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DistrictsNetResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.BiometricStatusMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.ChangePasswordMWResponse;
import bg.com.bo.bff.providers.dtos.response.personal.information.EconomyActivity;
import bg.com.bo.bff.providers.dtos.response.personal.information.PersonalInformationNetResponse;
import bg.com.bo.bff.providers.interfaces.IApiFaceNetProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.IPersonalInformationNetProvider;
import bg.com.bo.bff.providers.models.enums.middleware.login.LoginMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.response.user.UserControllerResponse;
import bg.com.bo.bff.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private static final String HAS_HUSBAND_LAST_NAME_NO = "N";
    private static final String MARRIED_AND_COMMON_LAW_UNION = "CU";
    private static final String BANK_EMPLOYEE = "3";
    private static final String DEPENDENT_INCOME_SOURCE = "D";
    private final ILoginMiddlewareProvider loginMiddlewareProvider;
    private final IPersonalInformationNetProvider personalInformationNetProvider;
    private final IPersonalInformationMapper iPersonalInformationMapper;
    private final IApiFaceNetProvider apiFaceNetProvider;
    private final IApiFaceMapper iApiFaceMapper;
    private final ILoginMapper mapper;

    @Autowired
    public UserService(
            ILoginMiddlewareProvider provider, IPersonalInformationNetProvider providerPersonal,
            IPersonalInformationMapper iPersonalInformationMapper, IApiFaceNetProvider apiFaceNetProvider,
            IApiFaceMapper iApiFaceMapper, ILoginMapper mapper
    ) {
        this.loginMiddlewareProvider = provider;
        this.personalInformationNetProvider = providerPersonal;
        this.iPersonalInformationMapper = iPersonalInformationMapper;
        this.apiFaceNetProvider = apiFaceNetProvider;
        this.iApiFaceMapper = iApiFaceMapper;
        this.mapper = mapper;
    }

    @Override
    public GenericResponse changePassword(
            String personId, String personRoleId, ChangePasswordRequest changePasswordRequest
    ) throws IOException {
        IValidator<String> validator = new MinLengthValidator(
                Constants.PASSWORD_MIN_LENGTH, new HandledException(LoginMiddlewareError.NOT_VALID_PASSWORD)
        );
        validator
                .setNext(
                        new MaxLengthValidator(
                                Constants.PASSWORD_MAX_LENGTH,
                                new HandledException(LoginMiddlewareError.NOT_VALID_PASSWORD)
                        )
                )
                .setNext(new ContainsDigitValidator(new HandledException(LoginMiddlewareError.NOT_VALID_PASSWORD)))
                .setNext(new ContainsLetterValidator(new HandledException(LoginMiddlewareError.NOT_VALID_PASSWORD)));

        validator.validate(changePasswordRequest.getNewPassword());
        ChangePasswordMWRequest mwRequest = mapper.mapperRequest(changePasswordRequest, personId, personRoleId);
        ChangePasswordMWResponse mwResponse = loginMiddlewareProvider.changePassword(mwRequest);
        if (mwResponse.getPersonId() == null) {
            throw new GenericException(AppError.DEFAULT);
        }
        return GenericResponse.instance(UserControllerResponse.SUCCESS);
    }

    @Override
    public BiometricsResponse getBiometrics(Integer personId) throws IOException {
        BiometricStatusMWResponse mwResponse = loginMiddlewareProvider.getBiometricsMW(personId);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public UpdateBiometricsResponse updateBiometrics(Integer personId, UpdateBiometricsRequest request) throws IOException {
        UpdateBiometricsMWRequest mwRequest = mapper.mapperUpdateBiometricRequest(request);
        return loginMiddlewareProvider.updateBiometricsMW(personId, mwRequest);
    }

    @Override
    public ContactResponse getContactInfo() {
        return loginMiddlewareProvider.getContactInfo();
    }

    @Override
    public PersonalResponse getPersonalInformation(String personId) throws IOException {
        ApiPersonalInformationNetRequest requestData = iPersonalInformationMapper.mapperRequest(personId);
        PersonalInformationNetResponse response = personalInformationNetProvider.getPersonalInformation(requestData);
        return iPersonalInformationMapper.convertRequest(response);
    }

    @Override
    public EconomicActivityResponse getEconomicActivity(Integer personId) {
        return personalInformationNetProvider.getEconomicalActivity(personId);
    }

    @Override
    public DepartmentsResponse getDepartments() throws IOException {
        DepartmentsNetResponse netResponse = apiFaceNetProvider.getDepartments();
        return iApiFaceMapper.mapToDepartmentsResponse(netResponse);
    }

    @Override
    public DistrictsResponse getDistricts(String departmentId) throws IOException {
        DistrictsNetRequest requestData = iPersonalInformationMapper.mapToDistrictRequest(String.valueOf(departmentId));
        DistrictsNetResponse netResponse = personalInformationNetProvider.getDistricts(requestData);

        return iPersonalInformationMapper.mapToDistrictsResponse(netResponse);
    }

    @Override
    public MaritalStatusResponse getMaritalStatus() {
        return personalInformationNetProvider.getMaritalStatuses();
    }

    @Override
    public GenericResponse updateDataUser(String personId, UpdateDataUserRequest request) throws IOException {

        ApiPersonalInformationNetRequest requestData = iPersonalInformationMapper.mapperRequest(personId);

        PersonalInformationNetResponse personalInformation = personalInformationNetProvider.getPersonalInformation(requestData);

        if (personalInformation.getDataContent().getClientDataList().isEmpty()) {
            AppError error = AppError.NOT_ACCEPTABLE_UPDATE_PERSONAL_INFORMATION;
            throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
        }

        request.setReference(validateReference(request.getReference(), personalInformation));

        request.setMaritalStatus(validateMaritalStatus(request.getMaritalStatus(),
                personalInformation.getDataContent().getClientDataList().get(0).getGender()));

        validateCityCode(request.getPersonalData().getDepartmentCode(),
                request.getPersonalData().getCityCode());

        UpdatePersonalDetail.EconomicalActivity economicalActivity = validateEconomicalActivity(
                personId,
                request.getEconomicalActivity(),
                request.getPersonalData().getBankEmployee(),
                personalInformation.getDataContent().getClientDataList().get(0).getIncomeLevel(),
                personalInformation.getDataContent().getClientDataList().get(0).getEconomicActivity(),
                personalInformation.getDataContent().getEconomicActivities().get(0)
        );

        request.setEconomicalActivity(economicalActivity);
        UpdatePersonalInformationNetRequest updatePersonalInformationNetRequest = iPersonalInformationMapper.convertRequest(
                personId, request, personalInformation
        );
        personalInformationNetProvider.updatePersonalInformation(updatePersonalInformationNetRequest);

        return GenericResponse.instance(UpdateDataUserResponse.SUCCESS);
    }

    private UpdatePersonalDetail.EconomicalActivity validateEconomicalActivity(
            String personId,
            UpdatePersonalDetail.EconomicalActivity economicalActivity,
            String bankEmployee,
            String incomeLevel,
            int economicActivity,
            EconomyActivity economyActivity
    ) {
        if (bankEmployee.equals(BANK_EMPLOYEE)) {
            return UpdatePersonalDetail.EconomicalActivity.builder()
                    .type(economyActivity.getIncomeSource())
                    .company(economyActivity.getCompany())
                    .position(economyActivity.getPosition())
                    .incomeLevel(Integer.valueOf(incomeLevel))
                    .economicActivity(economicActivity)
                    .build();
        }

        final EconomicActivityResponse economicActivityResponse = personalInformationNetProvider.getEconomicalActivity(
                Integer.valueOf(personId)
        );

        if (economicActivityResponse.getIncomeSource().stream().noneMatch(x -> x.getId().equals(
                economicalActivity.getType()))
        ) {
            throw new GenericException(
                    AppError.INCOME_SOURCE_NOT_EXIST.getMessage(),
                    AppError.INCOME_SOURCE_NOT_EXIST.getHttpCode(),
                    AppError.INCOME_SOURCE_NOT_EXIST.getCode()
            );
        }

        if (economicActivityResponse.getIncomeLevel().stream().noneMatch(x -> x.getId().equals(
                economicalActivity.getIncomeLevel().toString()))
        ) {
            throw new GenericException(
                    AppError.INCOME_LEVEL_NOT_EXIST.getMessage(),
                    AppError.INCOME_LEVEL_NOT_EXIST.getHttpCode(),
                    AppError.INCOME_LEVEL_NOT_EXIST.getCode()
            );
        }

        if (!economicalActivity.getType().equals(DEPENDENT_INCOME_SOURCE)) {
            return economicalActivity;
        }

        if (economicActivityResponse.getJobTitle().stream().noneMatch(x -> x.getId().equals(
                economicalActivity.getPosition()))
        ) {
            throw new GenericException(
                    AppError.POSITION_NOT_EXIST.getMessage(),
                    AppError.POSITION_NOT_EXIST.getHttpCode(),
                    AppError.POSITION_NOT_EXIST.getCode()
            );
        }

        if (economicActivityResponse.getEconomicActivity().stream().noneMatch(x -> x.getId().equals(
                String.valueOf(economicalActivity.getEconomicActivity())))
        ) {
            throw new GenericException(
                    AppError.ECONOMIC_ACTIVITY_NOT_EXIST.getMessage(),
                    AppError.ECONOMIC_ACTIVITY_NOT_EXIST.getHttpCode(),
                    AppError.ECONOMIC_ACTIVITY_NOT_EXIST.getCode());
        }

        if (economicalActivity.getCompany() == null) {
            throw new GenericException(
                    AppError.COMPANY_NAME_NOT_NULL.getMessage(),
                    AppError.COMPANY_NAME_NOT_NULL.getHttpCode(),
                    AppError.COMPANY_NAME_NOT_NULL.getCode()
            );
        }
        return economicalActivity;
    }

    private UpdatePersonalDetail.Reference validateReference(
            UpdatePersonalDetail.Reference reference,
            PersonalInformationNetResponse personalInformation
    ) {
        if (reference == null) {
            return UpdatePersonalDetail.Reference.builder()
                    .name(personalInformation.getDataContent().getReferences().get(0).getName())
                    .telephone(personalInformation.getDataContent().getReferences().get(0).getPhone())
                    .ordinal(personalInformation.getDataContent().getReferences().get(0).getOrdinal())
                    .relationship(personalInformation.getDataContent().getReferences().get(0).getRelation())
                    .build();
        }
        if (reference.getName() == null || reference.getTelephone() == null) {
            throw new GenericException(
                    AppError.REFERENCE_INVALID.getMessage(),
                    AppError.REFERENCE_INVALID.getHttpCode(),
                    AppError.REFERENCE_INVALID.getCode()
            );
        }
        if (reference.getName().isBlank()) {
            throw new GenericException(
                    AppError.REFERENCE_INVALID.getMessage(),
                    AppError.REFERENCE_INVALID.getHttpCode(),
                    AppError.REFERENCE_INVALID.getCode()
            );
        }

        if (reference.getTelephone().isBlank()) {
            throw new GenericException(
                    AppError.REFERENCE_INVALID.getMessage(),
                    AppError.REFERENCE_INVALID.getHttpCode(),
                    AppError.REFERENCE_INVALID.getCode()
            );
        }
        return reference;

    }

    private void validateCityCode(int departmentId, Integer cityCode) throws IOException {


        DistrictsNetRequest requestData = iPersonalInformationMapper.mapToDistrictRequest(String.valueOf(departmentId));
        DistrictsNetResponse netResponse = personalInformationNetProvider.getDistricts(requestData);

        if (netResponse.getResult().getData().stream().map(x -> Integer.valueOf(x.getCodeDistrict()))
                .anyMatch(data -> data.equals(cityCode))
        ) {
            return;
        }

        AppError error = AppError.CITY_CODE_NOT_EXIST;
        throw new GenericException(error.getMessage(), error.getHttpCode(), error.getCode());
    }

    private UpdatePersonalDetail.MaritalStatus validateMaritalStatus(
            UpdatePersonalDetail.MaritalStatus maritalStatus, String gender
    ) {

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

        if (maritalStatus.getStatus().equals(MaritalStatus.COMMON_LAW_UNION.getCode())) {
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