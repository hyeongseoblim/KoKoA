package com.web.kokoa.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String email;
}
