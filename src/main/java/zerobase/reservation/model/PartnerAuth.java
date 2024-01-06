package zerobase.reservation.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import zerobase.reservation.persist.entity.Partner;

import java.util.List;

public class PartnerAuth {

    @Data
    public static class SignIn{
        private String partnerName;
        private String password;
    }

    @Data
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUp{
        @NotNull
        private String partnerName;
        @NotNull
        private String password;
        private List<String> roles;

        public Partner toEntity(){
            return Partner.builder()
                    .partnerName(this.partnerName)
                    .password(this.password)
                    .roles(this.roles)
                    .build();
        }

        public SignUp fromEntity(Partner partner){
            return SignUp.builder()
                    .partnerName(partner.getPartnerName())
                    .password(partner.getPassword())
                    .roles(partner.getRoles())
                    .build();
        }
    }
}
