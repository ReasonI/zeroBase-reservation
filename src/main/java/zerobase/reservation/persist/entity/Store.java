package zerobase.reservation.persist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import zerobase.reservation.model.constants.StoreStatus;

import java.time.LocalDateTime;

@Builder
@Entity(name = "STORE")
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String location;

    private double lat;
    private double lon;

    @Column(length = 500)
    private String explanation;


    @ColumnDefault("'IN_USE'")
    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    @CreatedDate
    @Column(updatable =false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
