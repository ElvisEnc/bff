package bg.com.bo.bff.application.dtos.request.loyalty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyImageRequest {
    private List<FilePath> filePaths;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FilePath {
        private String file;
    }
}
