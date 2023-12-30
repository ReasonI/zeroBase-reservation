package zerobase.reservation.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {



    //매장
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 상점입니다.");

    private final HttpStatus httpStatus;
    private final String description;
}
