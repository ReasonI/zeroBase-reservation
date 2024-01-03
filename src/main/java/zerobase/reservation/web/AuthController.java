package zerobase.reservation.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservation.model.Auth;
import zerobase.reservation.security.TokenProvider;
import zerobase.reservation.service.MemberService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    /**
     * 회원가입을 위한 API
     * @param request
     * @return
     */

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {

        var result = this.memberService.register(request);
        return ResponseEntity.ok(result);
    }

    /**
     * 로그인용 API
     * @param request
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        //id,pw 일치하는지 확인
        var member = this.memberService.authenticate(request);
        //token 발급
        var token = this.tokenProvider.generateToken(member.getUsername(), member.getRoles());
        return ResponseEntity.ok(token);
    }
}
