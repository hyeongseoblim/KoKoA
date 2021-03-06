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

public class video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int groupid;
    private String title;
    private String url;
}
