package zerobase.reservation.model;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import zerobase.reservation.persist.entity.User;

import java.util.List;

public class Auth {

    @Data
    public static class SignIn {
        private String username;
        private String password;
    }

    @Data
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUp{

        @NotNull
        private String username;
        @NotNull
        private String password;
        private List<String> roles;

        public User toEntity() {
            return User.builder()
                    .userName(this.username)
                    .password(this.password)
                    .roles(this.roles)
                    .build();
        }

        public SignUp fromEntity(User user) {

            return SignUp.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles())
                    .build();
        }
    }
}
