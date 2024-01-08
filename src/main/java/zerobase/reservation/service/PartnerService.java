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
import zerobase.reservation.model.constants.VisitStatus;
import zerobase.reservation.model.reservation.CheckReservation;
import zerobase.reservation.model.reservation.ReservationDto;
import zerobase.reservation.persist.PartnerRepository;
import zerobase.reservation.persist.ReservationRepository;
import zerobase.reservation.persist.StoreRepository;
import zerobase.reservation.persist.UserRepository;
import zerobase.reservation.persist.entity.Partner;
import zerobase.reservation.persist.entity.Reservation;
import zerobase.reservation.persist.entity.Store;
import zerobase.reservation.persist.entity.User;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PartnerService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final PartnerRepository partnerRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

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

    /**
     * 예약 승인/거절
     */
    @Transactional
    public ReservationDto checkReservation(Long reservationId, CheckReservation.Request request, Principal principal){

        User user = userRepository.findByUserName(principal.getName())
                .orElseThrow(() -> new ReservationException(ErrorCode.USER_NOT_FOUND));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_FOUND));

        Store store = storeRepository.findById(reservation.getStore().getId())
                .orElseThrow(() -> new ReservationException(ErrorCode.STORE_NOT_FOUND));


        return ReservationDto.fromEntity(
                reservationRepository.save(
                        Reservation.builder()
                                .id(reservationId)
                                .store(store)
                                .user(user)
                                .reservationTime(reservation.getReservationTime())
                                .visitStatus(reservation.getVisitStatus())
                                .reserveStatus(request.getReserveStatus())
                                .updatedAt(LocalDateTime.now())
                                .build()
                )
        );
    }
}
