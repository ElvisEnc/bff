package bg.com.bo.bff.application.config;

import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

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
                if (!shouldExcludePath(path)) {
                    pathItem.readOperations().forEach(this::addHeaders);
                }
            });
        };
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    private boolean shouldExcludePath(String path) {
        return path.equals("/api/v1/login/{personId}/refresh") ||
                path.equals("/api/v1/users/contact") ||
                path.equals("/api/v1/registry/device/migration") ||
                path.equals("/api/v1/attention-points/points") ||
                path.equals("/api/v1/attention-points/points/{pointId}") ||
                path.equals("/api/v1/attention-points/points/{pointId}/tickets") ||
                path.equals("/api/v1/registry/device/handshake");
    }

    private void addHeaders(Operation operation) {
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
