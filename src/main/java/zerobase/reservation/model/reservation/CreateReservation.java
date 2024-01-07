package zerobase.reservation.model.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class CreateReservation {

    @Getter
    @Setter
    public static class Request{

        @NotNull
        private LocalDateTime reservationTime;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private Long id;

        private LocalDateTime reservationTime;
        private LocalDateTime createdAt;

        public static Response from(ReservationDto reservationDto){
            return Response.builder()
                    .id(reservationDto.getId())
                    .reservationTime(reservationDto.getReservationTime())
                    .createdAt(reservationDto.getCreatedTime())
                    .build();
        }
    }
}
