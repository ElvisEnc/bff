package bg.com.bo.bff.application.dtos.request;

@lombok.Data
public class ExtractRequest {
    private Filter filters;

    @lombok.Data
    public static class Filter {
        private Pagination pagination;
    }

    @lombok.Data
    public static class Pagination {
        private String startDate;
        private String endDate;
        private Integer page;
        private Integer pageSize;
    }

}
