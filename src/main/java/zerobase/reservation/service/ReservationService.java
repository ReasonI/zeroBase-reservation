package zerobase.reservation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.exception.ReservationException;
import zerobase.reservation.model.constants.ErrorCode;
import zerobase.reservation.model.constants.VisitStatus;
import zerobase.reservation.model.reservation.CreateReservation;
import zerobase.reservation.model.reservation.ReservationDto;
import zerobase.reservation.persist.ReservationRepository;
import zerobase.reservation.persist.StoreRepository;
import zerobase.reservation.persist.UserRepository;
import zerobase.reservation.persist.entity.Reservation;
import zerobase.reservation.persist.entity.Store;
import zerobase.reservation.persist.entity.User;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    /**
     * 예약 정보 저장
     */
    @Transactional
    public ReservationDto saveReserve(Long storeId, CreateReservation.Request request, Principal principal) {

        User user = userRepository.findByUserName(principal.getName())
                .orElseThrow(() -> new ReservationException(ErrorCode.USER_NOT_FOUND));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_FOUND));

        //예약 가능 시간은 현재로 부터 10분 전까지
        if(request.getReservationTime().isBefore(LocalDateTime.now().minusMinutes(10))){
            throw new ReservationException(ErrorCode.EXPIRED_TIME);
        }

        return ReservationDto.fromEntity(
                reservationRepository.save(
                        Reservation.builder()
                                .store(store)
                                .user(user)
                                .reservationTime(request.getReservationTime())
                                .build()
                ));
    }


    /**
     * 방문 확인
     */

    @Transactional
    public ReservationDto updateVisit(Long reservationId, Principal principal) {

        User user = userRepository.findByUserName(principal.getName())
                .orElseThrow(() -> new ReservationException(ErrorCode.USER_NOT_FOUND));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_FOUND));

        Store store = storeRepository.findById(reservation.getStore().getId())
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_FOUND));


        //예약 시간 10분 전
        if (LocalDateTime.now().isBefore(reservation.getReservationTime().plusMinutes(10))) {
            return ReservationDto.fromEntity(
                    reservationRepository.save(
                            Reservation.builder()
                                    .id(reservationId)
                                    .store(store)
                                    .user(user)
                                    .reservationTime(reservation.getReservationTime())
                                    .visitStatus(VisitStatus.CHECK_IN)
                                    .reserveStatus(reservation.getReserveStatus())
                                    .updatedAt(LocalDateTime.now())
                                    .build()
                    )
            );
        }
        // 예약 시간 10분 이후
        else {
            return ReservationDto.fromEntity(
                    reservationRepository.save(
                            Reservation.builder()
                                    .id(reservationId)
                                    .store(store)
                                    .user(user)
                                    .reservationTime(reservation.getReservationTime())
                                    .visitStatus(VisitStatus.TARDY)
                                    .reserveStatus(reservation.getReserveStatus())
                                    .updatedAt(LocalDateTime.now())
                                    .build()
                    )
            );
        }
    }
}
