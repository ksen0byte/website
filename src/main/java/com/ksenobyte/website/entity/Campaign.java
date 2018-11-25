package com.ksenobyte.website.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User owner;


}
