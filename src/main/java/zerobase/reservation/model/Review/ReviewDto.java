package zerobase.reservation.model.Review;

import lombok.*;
import zerobase.reservation.model.constants.ReviewStatus;
import zerobase.reservation.persist.entity.Review;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long id;

    private Long storeId;
    private Long userId;
    private Long reservationId;

    private String content;

    private int star;

    private ReviewStatus reviewStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReviewDto fromEntity(Review review){
        return ReviewDto.builder()
                .id(review.getId())
                .storeId(review.getStore().getId())
                .userId(review.getUser().getId())
                .reservationId(review.getReservation().getId())
                .content(review.getContent())
                .star(review.getStar())
                .reviewStatus(review.getReviewStatus())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();

    }
}
