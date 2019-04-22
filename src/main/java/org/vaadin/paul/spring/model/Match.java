package org.vaadin.paul.spring.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue
    private Long matchId;

    @Column
    private String name;

    @Column
    private Double odds;

    @Column
    private Date matchDate;

    @Column
    private String tips;

    @Column
    private Result result;

    @ManyToOne
    private Ticket parentTicket;

}
