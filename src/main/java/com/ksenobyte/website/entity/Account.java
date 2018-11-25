package com.ksenobyte.website.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    private String login;
    private String password;
}
