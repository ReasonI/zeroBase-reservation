package zerobase.reservation.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.model.Review.CreateReview;
import zerobase.reservation.model.Review.DeleteReview;
import zerobase.reservation.model.Review.UpdateReview;
import zerobase.reservation.service.ReviewService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 등록
     */

    @PostMapping("/{reservation-id}")
    @PreAuthorize("hasRole('USER_WRITE')")
    public CreateReview.Response addReview(@PathVariable("reservation-id") Long reservationId,
                                           @RequestBody CreateReview.Request request,
                                           Principal principal) {
        return CreateReview.Response.from(
                reviewService.saveReview(reservationId, request, principal)
        );
    }

    /**
     * 리뷰 수정
     */
    @PutMapping("/{review-id}")
    @PreAuthorize("hasRole('USER_WRITE')")
    public UpdateReview.Response updateReview(@PathVariable("review-id") Long reviewId,
                                              @RequestBody UpdateReview.Request request,
                                              Principal principal) {
        return UpdateReview.Response.from(
                reviewService.updateReview(reviewId, request, principal)
        );
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("/{review-id}")
    @PreAuthorize("hasAnyRole('USER_WRITE','PARTNER_WRITE')")
    public DeleteReview.Response deleteReview(@PathVariable("review-id") Long reviewId,
                                              Principal principal) {
        return DeleteReview.Response.from(
                reviewService.deleteReview(reviewId, principal)
        );
    }

}
