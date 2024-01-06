package zerobase.reservation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.exception.ReservationException;
import zerobase.reservation.model.Auth;
import zerobase.reservation.model.constants.ErrorCode;
import zerobase.reservation.persist.entity.User;
import zerobase.reservation.persist.UserRepository;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    /**
     * 회원가입된 아이디인지 확인
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.userRepository.findByUserName(username)
                .orElseThrow(() -> new ReservationException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 회원가입
     */
    @Transactional
    public Auth.SignUp register(Auth.SignUp member) {

        boolean exists = this.userRepository.existsByUserName(member.getUsername());
        if (exists) {
            throw new ReservationException(ErrorCode.USER_EXIST);
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));

        return member.fromEntity(this.userRepository.save(member.toEntity()));

    }

    /**
     * 로그인
     */
    @Transactional
    public User authenticate(Auth.SignIn member) {

        var user = this.userRepository.findByUserName(member.getUsername())
                .orElseThrow(() -> new ReservationException(ErrorCode.USER_NOT_FOUND));

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new ReservationException(ErrorCode.PASSWORD_NOT_FOUND);
        }
        return user;
    }
}
