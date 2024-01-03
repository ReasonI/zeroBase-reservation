package zerobase.reservation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.model.Auth;
import zerobase.reservation.persist.entity.MemberEntity;
import zerobase.reservation.persist.MemberRepository;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;


    /**회원가입된 아이디인지 확인
     *
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.memberRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("coudn't find user -> " + username));
    }

    /**회원가입
     *
     * @param member
     * @return
     */
    @Transactional
    public MemberEntity register(Auth.SignUp member){

        boolean exists = this.memberRepository.existsByUserName(member.getUsername());
        if(exists) {
            throw new RuntimeException("이미 사용 중인 아이디 입니다.");
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));

        var result = this.memberRepository.save(member.toEntity());
        System.out.println(result);

        return result;
    }

    /**로그인
     *
     * @param member
     * @return
     */
    @Transactional
    public MemberEntity authenticate(Auth.SignIn member){

        var user = this.memberRepository.findByUserName(member.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다"));

        if(!this.passwordEncoder.matches(member.getPassword(), user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        return user;
    }
}
