package zerobase.reservation.model.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import zerobase.reservation.model.constants.VisitStatus;

import java.time.LocalDateTime;

public class UpdateReservation {
    @Getter
    @Setter
    public static class Request {

        @NotNull
        private VisitStatus visitStatus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long id;

        private VisitStatus visitStatus;
        private LocalDateTime updatedAt;

        public static Response from(ReservationDto reservationDto) {
            return Response.builder()
                    .id(reservationDto.getId())
                    .visitStatus(reservationDto.getVisitStatus())
                    .updatedAt(reservationDto.getUpdatedTime())
                    .build();
        }

    }
}
