package com.hamrodelivery.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String heading;

    @Column
    private float ratings;

    @Column
    private String brand;

    @Column
    private float actualAmnt;

    @Column
    private float disAmnt;

}
