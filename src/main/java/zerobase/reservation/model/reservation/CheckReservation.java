package zerobase.reservation.model.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import zerobase.reservation.model.constants.ReserveStatus;

import java.time.LocalDateTime;

public class CheckReservation {

    @Getter
    @Setter
    public static class Request {

        @NotNull
        private ReserveStatus reserveStatus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long id;

        private ReserveStatus reserveStatus;
        private LocalDateTime updatedAt;

        public static CheckReservation.Response from(ReservationDto reservationDto) {
            return Response.builder()
                    .id(reservationDto.getId())
                    .reserveStatus(reservationDto.getReserveStatus())
                    .updatedAt(reservationDto.getUpdatedTime())
                    .build();
        }

    }
}
