package zerobase.reservation.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservation.model.PartnerAuth;
import zerobase.reservation.security.TokenProvider;
import zerobase.reservation.service.PartnerService;

//  0/3
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;
    private final TokenProvider tokenProvider;

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

        var token = this.tokenProvider.generateToken(partner.getUsername(), partner.getRoles());

        return ResponseEntity.ok(token);

    }

    /**
     * 예약 신청된 목록
     */

    /**
     * 예약 승인/거절
     */
}
