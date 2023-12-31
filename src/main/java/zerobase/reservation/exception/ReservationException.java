package zerobase.reservation.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import zerobase.reservation.model.constants.ErrorCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationException extends RuntimeException{
    private ErrorCode errorCode;
    private int status;
    private static final ObjectMapper mapper = new ObjectMapper();
//    private String errorMessage;

    public ReservationException(ErrorCode errorCode){
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.status = errorCode.getHttpStatus().value();

    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class ReservationExceptionResponse{
//        private ErrorCode errorCode;
        private int status;
        private String code;
        private String errorMessage;

    }
}
