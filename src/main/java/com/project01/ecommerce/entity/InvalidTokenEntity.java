package com.project01.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invalidatetoken")
public class InvalidTokenEntity {
    @Id
    private String id;

    @Column
    private Date expirytime;

    @Column
    private String token;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity user;
}
