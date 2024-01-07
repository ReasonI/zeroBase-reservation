package zerobase.reservation.persist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import zerobase.reservation.model.constants.ReserveStatus;
import zerobase.reservation.model.constants.VisitStatus;

import java.time.LocalDateTime;

@Builder
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = "RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Store.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime reservationTime;

    @ColumnDefault("'PENDING'")
    @Enumerated(EnumType.STRING)
    private VisitStatus visitStatus;

    @ColumnDefault("'PENDING'")
    @Enumerated(EnumType.STRING)
    private ReserveStatus reserveStatus;

    @CreatedDate
    @Column(updatable =false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
