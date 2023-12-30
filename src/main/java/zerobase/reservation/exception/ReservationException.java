package zerobase.reservation.exception;


import lombok.*;
import zerobase.reservation.model.constants.ErrorCode;

@Getter
@AllArgsConstructor
@Builder
public class ReservationException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public ReservationException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
