package zerobase.reservation.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.exception.ReservationException;
import zerobase.reservation.model.PartnerAuth;
import zerobase.reservation.model.constants.ErrorCode;
import zerobase.reservation.persist.PartnerRepository;
import zerobase.reservation.persist.entity.Partner;

@Service
@AllArgsConstructor
public class PartnerService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final PartnerRepository partnerRepository;

    /**
     * 회원가입 된 아이디인지 확인
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.partnerRepository.findByPartnerName(username)
                .orElseThrow(() -> new ReservationException((ErrorCode.USER_NOT_FOUND)));

    }

    @Transactional
    public PartnerAuth.SignUp register(PartnerAuth.SignUp member){
        boolean exists = this.partnerRepository.existsByPartnerName(member.getPartnerName());
        if(exists){
            throw new ReservationException(ErrorCode.USER_EXIST);
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));

        return member.fromEntity(this.partnerRepository.save(member.toEntity()));
    }

    /**
     * 로그인
     */

    @Transactional
    public Partner authenticate(PartnerAuth.SignIn member){
        var partner = this.partnerRepository.findByPartnerName(member.getPartnerName())
                .orElseThrow(() -> new ReservationException(ErrorCode.USER_NOT_FOUND));

        if(!this.passwordEncoder.matches(member.getPassword(), partner.getPassword())) {
            throw new ReservationException(ErrorCode.PASSWORD_NOT_FOUND);
        }
        return partner;
    }
}
