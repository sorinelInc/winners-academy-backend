package com.winnersacademy.entity;

import com.winnersacademy.model.TicketType;
import com.winnersacademy.model.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
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
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Match> matchList = new ArrayList<>();

    @Column
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    public void addMatch(Match newMatch) {
        matchList.add(newMatch);
        newMatch.setParentTicket(this);
        computeResult();
        computeOdds();
    }

    public void removeMatch(Match match) {
        matchList.remove(match);
        match.setParentTicket(null);
        computeResult();
        computeOdds();
    }

    private void computeResult() {
        if (matchList.isEmpty()) {
            status = Result.NOT_DECIDED;
        } else {
            List<Result> results = matchList.stream()
                    .map(Match::getResult)
                    .collect(Collectors.toList());

            if (results.stream().anyMatch(result -> result.equals(Result.LOST))) {
                status = Result.LOST;
            } else if (results.stream().allMatch(result -> result.equals(Result.WON))) {
                status = Result.WON;
            } else {
                status = Result.NOT_DECIDED;
            }
        }
    }

    private void computeOdds() {
        if (matchList.isEmpty()) {
            totalOdds = 1.00D;
        } else {
            List<Double> odds = matchList.stream()
                    .map(Match::getOdds)
                    .collect(Collectors.toList());

            Double result = odds.stream().reduce(1D, (a, b) -> a * b);
            totalOdds = new BigDecimal(result)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }
    }
}
