package zerobase.reservation.model.reservation;

import lombok.*;
import zerobase.reservation.model.constants.ReserveStatus;
import zerobase.reservation.model.constants.VisitStatus;
import zerobase.reservation.persist.entity.Reservation;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private Long id;

    private Long storeId;
    private Long userId;

    private LocalDateTime reservationTime;

    private VisitStatus visitStatus;
    private ReserveStatus reserveStatus;

    private LocalDateTime createdTime;

    public static ReservationDto fromEntity(Reservation reservation){
        return ReservationDto.builder()
                .id(reservation.getId())
                .storeId(reservation.getStore().getId())
                .userId(reservation.getUser().getId())
                .reservationTime(reservation.getReservationTime())
                .visitStatus(reservation.getVisitStatus())
                .reserveStatus(reservation.getReserveStatus())
                .createdTime(reservation.getCreatedAt())
                .build();
    }
}
