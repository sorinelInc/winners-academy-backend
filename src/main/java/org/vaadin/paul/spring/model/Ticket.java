package org.vaadin.paul.spring.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue
    private Long ticketId;

    @Column
    private Double totalOdds;

    @Column
    @Enumerated(EnumType.STRING)
    private Result status;

    @Column
    private Date date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Match> matchList = new ArrayList<>();

    public void addMatch(Match newMatch){
        matchList.add(newMatch);
        newMatch.setParentTicket(this);
    }

}
