package org.vaadin.paul.spring.views.matches;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.paul.spring.model.Match;
import org.vaadin.paul.spring.model.Ticket;
import org.vaadin.paul.spring.repository.TicketRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class SecondPage extends VerticalLayout implements MatchPage {

    private TicketRepository ticketRepository;

    public SecondPage(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
        init();

        List<Match> matches = getMatches();

        Label totalOdds = new Label("Total Odds: ");
        String displayOdds = calculateTotalOdds(getMatches());
        totalOdds.add(displayOdds);

        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, totalOdds);

        Label result = new Label("Result: ");
        result.add(calculateResult(matches).toString());
        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, result);

        add(refresh, grid, totalOdds, result);
    }

    @Override
    public List<Match> getMatches() {
        LocalDate yersterday = LocalDate.now().minusDays(1);
        Ticket current = ticketRepository.findByDate(Date.valueOf(yersterday));
        return current.getMatchList();
    }
}
