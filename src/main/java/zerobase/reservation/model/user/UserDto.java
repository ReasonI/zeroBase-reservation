package zerobase.reservation.model.user;

import lombok.*;
import zerobase.reservation.model.constants.UserStatus;
import zerobase.reservation.persist.entity.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String userName;
    private String password;

    private List<String> roles;

    private UserStatus userStatus;

    public static UserDto fromEntity(User user){
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .userStatus(user.getUserStatus())
                .build();
    }
}
