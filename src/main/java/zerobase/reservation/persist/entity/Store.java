package zerobase.reservation.persist.entity;

import jakarta.persistence.*;

@Entity(name = "STORE")
public class Store {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    private String location;

    private double lat;
    private double lon;

    private int star;

    @Column(length = 500)
    private String explanation;

}
