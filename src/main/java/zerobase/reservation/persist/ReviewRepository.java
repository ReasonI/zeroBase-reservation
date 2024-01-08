package zerobase.reservation.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservation.persist.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByReservationId(Long reservationId);
}
