package org.vaadin.paul.spring.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Match2 {

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
    private Date date;

    @Column
    private Double odds;

    @ManyToOne
    private Ticket parentTicket;
}
