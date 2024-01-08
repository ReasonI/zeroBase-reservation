package zerobase.reservation.model.Review;

import lombok.*;
import zerobase.reservation.model.constants.ReviewStatus;
import zerobase.reservation.persist.entity.Review;

public class DeleteReview {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long id;
        private ReviewStatus reviewStatus;

        public static DeleteReview.Response from(ReviewDto reviewDto) {
            return Response.builder()
                    .id(reviewDto.getId())
                    .reviewStatus(reviewDto.getReviewStatus())
                    .build();
        }
    }
}
