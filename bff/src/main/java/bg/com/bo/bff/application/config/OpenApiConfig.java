package bg.com.bo.bff.application.config;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.EnvProfile;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.enums.config.provider.EncryptionHeaders;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.util.*;

@Configuration
public class OpenApiConfig {
    private final Environment env;

    public OpenApiConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("BFF")
                        .description("Documentación de la API BFF")
                        .version("1.0"));
    }

    @Bean
    public OpenApiCustomizer customizeOpenApi() {
        return openApi -> {
            Paths paths = openApi.getPaths();
            paths.forEach((path, pathItem) -> {
                if (!shouldExcludePathFromDeviceHeaders(path))
                    pathItem.readOperations().forEach(this::addDeviceHeaders);
                if (!shouldExcludePathFromEncryptionHeaders(path))
                    pathItem.readOperations().forEach(this::addEncryptionHeaders);
                pathItem.readOperations().forEach(this::addGenericErrors);
            });
        };
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    private boolean shouldExcludePathFromEncryptionHeaders(String path) {
        return path.equals("/api/v1/login/{personId}/refresh") ||
                path.equals("/api/v1/attention-points/points") ||
                path.equals("/api/v1/attention-points/points/{pointId}") ||
                path.equals("/api/v1/attention-points/points/{pointId}/tickets") ||
                path.equals("/api/v1/registry/device/handshake");
    }

    private boolean shouldExcludePathFromDeviceHeaders(String path) {
        return path.equals("/api/v1/login/{personId}/refresh") ||
                path.equals("/api/v1/users/contact") ||
                path.equals("/api/v1/registry/device/migration") ||
                path.equals("/api/v1/attention-points/points") ||
                path.equals("/api/v1/attention-points/points/{pointId}") ||
                path.equals("/api/v1/attention-points/points/{pointId}/tickets") ||
                path.equals("/api/v1/registry/device/handshake");
    }

    private void addGenericErrors(Operation operation) {
        operation.getResponses().addApiResponse("400", createGenericErrorApiResponse(
                "Indica que hubo un error en los parámetros otorgados.",
                "BAD_REQUEST",
                "Error en los parámetros.",
                "Error",
                "1200"
        ));

        operation.getResponses().addApiResponse("401", createGenericErrorApiResponse(
                "Indica que hubo problemas en la autenticación/autorización del usuario.",
                "BFF-DNAVU",
                "Usuario autenticado no válido.",
                "Usuario no válido",
                "1100"
        ));

        operation.getResponses().addApiResponse("403", createGenericErrorApiResponse(
                "Indica que el usuario no tiene permisos.",
                "BFF-F",
                "Usuario no autenticado.",
                "Usuario no autenticado",
                "1100"
        ));

        operation.getResponses().addApiResponse("406", createGenericErrorApiResponse(
                "Indica que hubo problemas con el MW.",
                "INVALID_DATA",
                "No se pudo agregar la cuenta. Inténtalo nuevamente.",
                "Ocurrió un problema",
                "1300"
        ));

        operation.getResponses().addApiResponse("409", createGenericErrorApiResponse(
                "Identifica que el cliente/app manda la información con un formato correcto pero el MW nos indica que el resultado no es el esperado.",
                "TRANSACTION_NOT_ALLOWED",
                "Las transacciones en moneda extranjera están temporalmente deshabilitadas.",
                "Transacción no disponible",
                "1400"
        ));

        operation.getResponses().addApiResponse("500", createGenericErrorApiResponse(
                "Indica que hubo un error interno.",
                "INTERNAL_SERVER_ERROR",
                "Hubo un error interno.",
                "Error interno",
                "1000"
        ));

        operation.getResponses().addApiResponse("501", createGenericErrorApiResponse(
                "Indica que hay algo que no es correcto dentro del BFF al enviar/recibir un request al MW.",
                "INVALID_VERSION",
                "La versión es inválida.",
                "Versión inválida",
                "1502"
        ));

        operation.getResponses().addApiResponse("503", createGenericErrorApiResponse(
                "Indica que alguno de los componentes del BFF no esta disponible.",
                "INVALID_VERSION",
                "La versión es inválida.",
                "Versión inválida",
                "1502"
        ));
    }

    private static ApiResponse createGenericErrorApiResponse(String description, String code, String message, String title, String categoryId) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))
                                .example(Map.of(
                                        "code", code,
                                        "message", message,
                                        "title", title,
                                        "categoryId", categoryId
                                ))));
    }

    private void addEncryptionHeaders(Operation operation) {
        String paramInType = "header";
        Parameter sekHeader = new Parameter().in(paramInType)
                .name(EncryptionHeaders.SESSION_ENCRYPT_KEY_HEADER.getCode())
                .description("Session encrypted key")
                .required(false)
                .schema(new StringSchema())
                .example("QA56UutnnDqRd8538giU2f5zkVsPE3id7tv...");
        operation.addParametersItem(sekHeader);

        Parameter eiHeader = new Parameter().in(paramInType)
                .name(EncryptionHeaders.ENCRYPT_INFO_HEADER.getCode())
                .description("Encrypt info")
                .required(false)
                .schema(new StringSchema())
                .example("eyJwdWJsaWMiOiJNSUlDSWpBTkJna3Foa2lc...");
        operation.addParametersItem(eiHeader);

        if (!Arrays.stream(env.getActiveProfiles()).toList().contains(EnvProfile.prod.name())) {
            Parameter eekHeader = new Parameter().in(paramInType)
                    .name(EncryptionHeaders.ENCRYPTION_EXCLUDED_KEY_HEADER.getCode())
                    .description("Exclude encryption key")
                    .required(false)
                    .schema(new StringSchema())
                    .example("1DlrmwRqPbQqabsxBd1dXfiUlqrCazkjsjcda...");
            operation.addParametersItem(eekHeader);
        }
    }

    private void addDeviceHeaders(Operation operation) {
        String paramInType = "header";
        Parameter deviceIdHeader = new Parameter().in(paramInType)
                .name(DeviceMW.DEVICE_ID.getCode())
                .description("Device ID")
                .required(true)
                .schema(new StringSchema())
                .example("42ebffbd7c30307d");

        Parameter deviceNameHeader = new Parameter().in(paramInType)
                .name(DeviceMW.DEVICE_NAME.getCode())
                .description("Device Name")
                .required(true)
                .schema(new StringSchema())
                .example("ANDROID");

        Parameter geoPositionXHeader = new Parameter().in(paramInType)
                .name(DeviceMW.GEO_POSITION_X.getCode())
                .description("Geo Position X")
                .required(true)
                .schema(new StringSchema())
                .example("12.265656");

        Parameter geoPositionYHeader = new Parameter().in(paramInType)
                .name(DeviceMW.GEO_POSITION_Y.getCode())
                .description("Geo Position Y")
                .required(true)
                .schema(new StringSchema())
                .example("12.454545");

        Parameter appVersionHeader = new Parameter().in(paramInType)
                .name(DeviceMW.APP_VERSION.getCode())
                .description("App Version")
                .required(true)
                .schema(new StringSchema())
                .example("1.3.3");

        operation.addParametersItem(deviceIdHeader);
        operation.addParametersItem(deviceNameHeader);
        operation.addParametersItem(geoPositionXHeader);
        operation.addParametersItem(geoPositionYHeader);
        operation.addParametersItem(appVersionHeader);
    }
}
