package zerobase.reservation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.exception.ReservationException;
import zerobase.reservation.model.constants.ErrorCode;
import zerobase.reservation.model.reservation.CreateReservation;
import zerobase.reservation.model.reservation.ReservationDto;
import zerobase.reservation.persist.ReservationRepository;
import zerobase.reservation.persist.StoreRepository;
import zerobase.reservation.persist.UserRepository;
import zerobase.reservation.persist.entity.Reservation;
import zerobase.reservation.persist.entity.Store;
import zerobase.reservation.persist.entity.User;

import java.security.Principal;

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
        //TODO : 현재 시간을 지난 reservationTime validate

        return ReservationDto.fromEntity(
                reservationRepository.save(
                        Reservation.builder()
                                .store(store)
                                .user(user)
                                .reservationTime(request.getReservationTime())
                                .build()
                ));
    }
}
