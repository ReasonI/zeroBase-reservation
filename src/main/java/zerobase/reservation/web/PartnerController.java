package zerobase.reservation.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.model.PartnerAuth;
import zerobase.reservation.model.reservation.CheckReservation;
import zerobase.reservation.security.TokenProvider;
import zerobase.reservation.service.PartnerService;

import java.security.Principal;

//  0/3
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;
    private final TokenProvider tokenProvider;

    private static final String identify = "PARTNER";


    /**
     * 점주 회원가입
     */

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Validated PartnerAuth.SignUp request) {
        var result = this.partnerService.register(request);
        return ResponseEntity.ok(result);
    }
    /**
     * 점주 로그인
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody PartnerAuth.SignIn request) {
        var partner = this.partnerService.authenticate(request);

        var token = this.tokenProvider.generateToken(identify, partner.getUsername(), partner.getRoles());

        return ResponseEntity.ok(token);
    }


    /**
     * 예약 승인/거절
     */
    @PutMapping("/reservation/{reservation-id}")
    public CheckReservation.Response checkReservation(@PathVariable("reservation-id") Long reservationId,
                                                      @RequestBody CheckReservation.Request request,
                                                      Principal principal){
        return CheckReservation.Response.from(
                partnerService.checkReservation(reservationId, request, principal)
        );
    }
}
