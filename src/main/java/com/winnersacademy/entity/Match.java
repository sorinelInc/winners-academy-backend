package com.winnersacademy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.winnersacademy.model.Result;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "parentTicket")
public class Match {

    @Id
    @GeneratedValue
    private Long matchId;

    @Column
    private String name;

    @Column
    private String tips;

    @Column
    @Enumerated(EnumType.STRING)
    private Result result;

    @Column
    private LocalDate date;

    @Column
    private Double odds;

    @ManyToOne
    @JsonIgnore
    private Ticket parentTicket;
}
