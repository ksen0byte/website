package com.ksenobyte.website.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Entity
@Table
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String picture;
    private Locale locale;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDateTime lastVisit;

    @OneToMany
    private List<Campaign> ownedCampaigns = new ArrayList<>();
}
