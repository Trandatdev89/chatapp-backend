package com.project01.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class MessageEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "senderid")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "recipientid")
    private UserEntity recipient;

    @ManyToOne
    @JoinColumn(name = "chatid")
    private ChatRoomEntity chatRoom;

    @Column
    private String content;

}
