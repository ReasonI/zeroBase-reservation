package zerobase.reservation.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ReservationException.class})
    public ResponseEntity<ReservationException.ReservationExceptionResponse> exceptionHandler(HttpServletRequest request, final ReservationException e){
        return ResponseEntity
                .status(e.getStatus())
                .body(ReservationException.ReservationExceptionResponse.builder()
                        .errorMessage(e.getMessage())
                        .code(e.getErrorCode().name())
                        .status(e.getStatus()).build());


    }
}
