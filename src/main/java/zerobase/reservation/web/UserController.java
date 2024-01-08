package zerobase.reservation.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservation.model.Auth;
import zerobase.reservation.security.TokenProvider;
import zerobase.reservation.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    private static final String identify = "USER";

    /**
     * 회원가입
     * @param request
     * @return
     */

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Validated Auth.SignUp request) {

        var result = this.userService.register(request);
        return ResponseEntity.ok(result);
    }

    /**
     * 로그인
     * @param request
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        //id,pw 일치하는지 확인
        var member = this.userService.authenticate(request);

        //token 발급
        var token = this.tokenProvider.generateToken(identify, member.getUsername(), member.getRoles());
        return ResponseEntity.ok(token);
    }
}
