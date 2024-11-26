package bg.com.bo.bff.application.exceptions.fixture;

import bg.com.bo.bff.commons.annotations.Numeric;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@Setter
@RestController
@RequestMapping(MockedController.MOCKED_CONTROLLER)
public class MockedController {
    public static final String MOCKED_CONTROLLER = "mocked-controller";

    // Messages
    public static final String NOT_BLANK = "Not blank";
    public static final String NOT_VALID_REGEX = "Not valid regex";
    public static final String NOT_MIN = "Not min";

    // Urls
    public static final String EP_WITH_REQUIRED_AND_NON_BLANK_HEADER = "/with-required-and-not-blank-header";
    public static final String EP_WITH_REQUIRED_AND_NON_BLANK_AND_REGEX_HEADER = "/with-required-and-not-blank-and-regex-header";
    public static final String EP_WITH_REQUIRED_AND_NUMERIC_HEADER = "/with-required-and-numeric-header";
    public static final String EP_WITH_REQUIRED_BODY = "/with-required-body";
    public static final String EP_WITH_REQUIRED_BIG_DECIMAL_REQUEST_PARAM = "/with-required-big-decimal-request-param";

    // Headers
    public static final String REQUEST_HEADER = "request-header";
    public static final String BIG_DECIMAL_REQUEST_PARAM = "bigDecimalParam";

    private Throwable exception;

    @GetMapping(EP_WITH_REQUIRED_AND_NON_BLANK_HEADER)
    public ResponseEntity<String> getWithRequiredAndNotBlankHeader(
            @RequestHeader(value = REQUEST_HEADER)
            @NotBlank(message = MockedController.NOT_BLANK)
            String requestParam) throws Throwable {
        return getResponse();
    }

    @GetMapping(EP_WITH_REQUIRED_AND_NON_BLANK_AND_REGEX_HEADER)
    public ResponseEntity<String> getWithRequiredAndNotBlankAndRegexHeader(
            @RequestHeader(value = REQUEST_HEADER)
            @NotBlank(message = MockedController.NOT_BLANK)
            @Pattern(regexp = "d+", message = MockedController.NOT_VALID_REGEX)
            String requestParam) throws Throwable {
        return getResponse();
    }

    @GetMapping(EP_WITH_REQUIRED_AND_NUMERIC_HEADER)
    public ResponseEntity<String> getWithRequiredAndNumericHeader(
            @RequestHeader(value = REQUEST_HEADER)
            @Min(value = 5, message = MockedController.NOT_MIN)
            Integer requestParam) throws Throwable {
        return getResponse();
    }

    @PostMapping(EP_WITH_REQUIRED_BODY)
    public ResponseEntity<String> getWithRequiredBody(
            @Valid @RequestBody BodyParam bodyParam) throws Throwable {
        return getResponse();
    }

    @GetMapping(EP_WITH_REQUIRED_BIG_DECIMAL_REQUEST_PARAM)
    public ResponseEntity<String> getWithRequiredAndNumericHeader(
            @RequestParam(BIG_DECIMAL_REQUEST_PARAM) @Numeric(min = "1", max = "5", fieldName = BIG_DECIMAL_REQUEST_PARAM, numericType = BigDecimal.class) String bigDecimalParam) throws Throwable {
        return getResponse();
    }

    private ResponseEntity<String> getResponse() throws Throwable {
        if (this.exception != null) throw this.exception;
        return ResponseEntity.ok("PASSED");
    }
}
