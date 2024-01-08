package zerobase.reservation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.exception.ReservationException;
import zerobase.reservation.model.Review.CreateReview;
import zerobase.reservation.model.Review.ReviewDto;
import zerobase.reservation.model.Review.UpdateReview;
import zerobase.reservation.model.constants.ErrorCode;
import zerobase.reservation.model.constants.ReviewStatus;
import zerobase.reservation.model.constants.VisitStatus;
import zerobase.reservation.persist.ReservationRepository;
import zerobase.reservation.persist.ReviewRepository;
import zerobase.reservation.persist.StoreRepository;
import zerobase.reservation.persist.UserRepository;
import zerobase.reservation.persist.entity.Reservation;
import zerobase.reservation.persist.entity.Review;
import zerobase.reservation.persist.entity.Store;
import zerobase.reservation.persist.entity.User;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    /**
     * 리뷰 등록
     * 예약 방문 했는지 확인
     */
    @Transactional
    public ReviewDto saveReview(Long reservationId, CreateReview.Request request, Principal principal){

        User user = userRepository.findByUserName(principal.getName())
                .orElseThrow(() -> new ReservationException(ErrorCode.USER_NOT_FOUND));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_FOUND));

        //이미 등록 했는지 확인
        boolean exists = reviewRepository.existsByReservationId(reservationId);
        if(exists){
            throw new ReservationException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

        //예약 방문 했는지 확인
        if(reservation.getVisitStatus() != VisitStatus.CHECK_IN){
            throw new ReservationException(ErrorCode.BEFORE_VISIT);
        }


        return ReviewDto.fromEntity(
                reviewRepository.save(
                        Review.builder()
                                .user(user)
                                .store(reservation.getStore())
                                .reservation(reservation)
                                .content(request.getContent())
                                .star(request.getStar())
                                .build()
                ));
    }

    /**
     * 리뷰 수정
     */

    @Transactional
    public ReviewDto updateReview(Long reviewId, UpdateReview.Request request, Principal principal){

        User user = userRepository.findByUserName(principal.getName())
                .orElseThrow(() -> new ReservationException(ErrorCode.USER_NOT_FOUND));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReservationException(ErrorCode.REVIEW_NOT_FOUND));

        return ReviewDto.fromEntity(
                reviewRepository.save(
                        Review.builder()
                                .id(reviewId)
                                .store(review.getStore())
                                .user(user)
                                .reservation(review.getReservation())
                                .reviewStatus(ReviewStatus.IN_USE)
                                .content(request.getContent())
                                .createdAt(review.getCreatedAt())
                                .updatedAt(LocalDateTime.now())
                                .star(request.getStar())
                                .build()
                ));
    }

    /**
     * 리뷰 삭제
     */

    @Transactional
    public ReviewDto deleteReview(Long reviewId, Principal principal){

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReservationException(ErrorCode.REVIEW_NOT_FOUND));

        return ReviewDto.fromEntity(
                reviewRepository.save(
                        Review.builder()
                                .id(reviewId)
                                .store(review.getStore())
                                .user(review.getUser())
                                .reservation(review.getReservation())
                                .reviewStatus(ReviewStatus.DELETED)
                                .content(review.getContent())
                                .star(review.getStar())
                                .createdAt(review.getCreatedAt())
                                .updatedAt(LocalDateTime.now())
                                .build()
                ));
    }
}
