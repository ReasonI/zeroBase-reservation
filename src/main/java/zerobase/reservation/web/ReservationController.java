package zerobase.reservation.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.model.reservation.CreateReservation;
import zerobase.reservation.service.ReservationService;

import java.security.Principal;

//  0/4
@RestController
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReservationController {

    private final ReservationService reservationService;
    /**
     * 예약
     */
    @PostMapping("/{store-id}")
//    @PreAuthorize("hasRole('USER_WRITE')")
    public CreateReservation.Response saveReserve(@PathVariable("store-id") Long storeId,
                                                  @RequestBody @Valid CreateReservation.Request request,
                                                  Principal principal) {
        return CreateReservation.Response.from(
                reservationService.saveReserve(storeId, request, principal));
    }

    /**
     * 예약 현황 목록
     */

    /**
     * 예약 상세 정보
     */

    /**
     * 방문 확인
     */
}
