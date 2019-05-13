package org.vaadin.paul.spring.views.matches;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import org.vaadin.paul.spring.model.Match;
import org.vaadin.paul.spring.model.Result;
import org.vaadin.paul.spring.repository.TicketRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public interface MatchPage {

    Button refresh = new Button("Refresh");

    Grid<Match> grid = new Grid<>();

    List<Match> getMatches();

    default void init(){
        refresh.setVisible(false);
        refresh.addClickListener(buttonClickEvent -> {
            this.grid.setItems(getMatches());
        });

        grid.setHeightByRows(true);
        grid.setWidth("800px");

        grid.addColumn(Match::getName).setWidth("200px").setHeader("Match");
        grid.addColumn(Match::getTips).setHeader("Tip");
        grid.addColumn(Match::getDate).setHeader("Date");
        grid.addColumn(match -> Double.toString(match.getOdds())).setHeader("Odds");
        grid.addColumn(Match::getResult).setHeader("Result");
    }


    default void update() {
        refresh.click();
    }

    default Result calculateResult(List<Match> matches) {
        if (matches.stream().anyMatch(match -> match.getResult().equals(Result.LOST))) {
            return Result.LOST;
        }
        if (matches.stream().allMatch(match -> match.getResult().equals(Result.WON))) {
            return Result.WON;
        }
        return Result.NOT_DECIDED;
    }

    default String calculateTotalOdds(List<Match> matches){
        Double totalDouble = matches.stream().mapToDouble(Match::getOdds)
                .reduce(1, (first, second) -> first * second);

        BigDecimal displayOdds = new BigDecimal(totalDouble).setScale(2, RoundingMode.HALF_UP);
        return displayOdds.toPlainString();
    }

}
