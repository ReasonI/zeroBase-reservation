package zerobase.reservation.model.Review;

import lombok.*;

import java.time.LocalDateTime;

public class UpdateReview {

    @Getter
    @Setter
    public static class Request {
        private String content;
        private int star;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private LocalDateTime updatedAt;
        public static UpdateReview.Response from(ReviewDto reviewDto) {
            return UpdateReview.Response.builder()
                    .id(reviewDto.getId())
                    .updatedAt(reviewDto.getUpdatedAt())
                    .build();
        }
    }
}
