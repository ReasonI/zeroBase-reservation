package zerobase.reservation.model.Review;

import lombok.*;

import java.time.LocalDateTime;


public class CreateReview {

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
        private LocalDateTime createdAt;
        public static CreateReview.Response from(ReviewDto reviewDto) {
            return Response.builder()
                    .id(reviewDto.getId())
                    .createdAt(reviewDto.getCreatedAt())
                    .build();
        }
    }
}
