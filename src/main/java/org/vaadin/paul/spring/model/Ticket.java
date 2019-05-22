package org.vaadin.paul.spring.model;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Match> matchList = new ArrayList<>();

    public void addMatch(Match newMatch){
        matchList.add(newMatch);
        newMatch.setParentTicket(this);
    }

    public void removeMatch(Match match){
        matchList.remove(match);
        match.setParentTicket(null);
    }

}
