package zerobase.reservation.persist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import zerobase.reservation.model.constants.ReviewStatus;
import zerobase.reservation.model.constants.StoreStatus;

import java.time.LocalDateTime;

@Builder
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = "REVIEW")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private String content;

    private int star;

    @ColumnDefault("'IN_USE'")
    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

    @CreatedDate
    @Column(updatable =false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
