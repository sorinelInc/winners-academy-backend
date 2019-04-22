package org.vaadin.paul.spring.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.paul.spring.model.Match;
import org.vaadin.paul.spring.model.Ticket;
import org.vaadin.paul.spring.views.matches.TestDiv;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route(value = "daily",  layout = MainView.class)
@PageTitle("Daily Matches")
public class DailyView extends VerticalLayout {

    public DailyView() {
        VerticalLayout layout = new VerticalLayout();
        LocalDate today = LocalDate.now();

        Tab tab1 = new Tab(today.toString());
        Tab tab2 = new Tab(today.minusDays(1).toString());
        Tab tab3 = new Tab(today.minusDays(2).toString());
        Tab tab4 = new Tab(today.minusDays(3).toString());
        Tab tab5 = new Tab(today.minusDays(4).toString());
        Tab tab6 = new Tab(today.minusDays(5).toString());
        Tab tab7 = new Tab(today.minusDays(6).toString());

        Div page1 = new Div();
        page1.setText("Page#1");

        TestDiv page2 = new TestDiv();
        page2.setVisible(false);

        Div page3 = new Div();
        page3.setText("Page#3");
        page3.setVisible(false);

        Div page4 = new Div();
        page4.setText("Page#4");
        page4.setVisible(false);

        Div page5 = new Div();
        page5.setText("Page#5");
        page5.setVisible(false);

        Div page6 = new Div();
        page6.setText("Page#6");
        page6.setVisible(false);

        Div page7 = new Div();
        page7.setText("Page#7");
        page7.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tab1, page1);
        tabsToPages.put(tab2, page2);
        tabsToPages.put(tab3, page3);
        tabsToPages.put(tab4, page4);
        tabsToPages.put(tab5, page5);
        tabsToPages.put(tab6, page6);
        tabsToPages.put(tab7, page7);
        Tabs tabs = new Tabs(tab1, tab2, tab3, tab4, tab5, tab6, tab7);
        Div pages = new Div(page1, page2, page3, page4, page5, page6, page7);
        Set<Component> pagesShown = Stream.of(page1)
                .collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            if (selectedPage.equals(page2)) {
                page2.update();
            }
            pagesShown.add(selectedPage);
        });

        setHorizontalComponentAlignment(Alignment.CENTER, tabs, pages, layout);

        layout.add();
        layout.add(tabs, pages);
        add(layout);

    }


    private Grid<Ticket> createTicketGrid() {
        Grid<Ticket> grid = new Grid<>();
        grid.addColumn(Ticket::getDate).setHeader("Ticket Date")
                .setWidth("160px");
        grid.addColumn(Ticket::getTotalOdds).setHeader("Total Odds");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        return grid;
    }


    private ListDataProvider<Ticket> createTicketDataProvider() {
        List<Ticket> data = new ArrayList<>();

        data.add(Ticket.builder().date(new Date(System.currentTimeMillis()))
                .totalOdds(3.45D)
                .matchList(new ArrayList<>(Arrays.asList(Match.builder()
                        .name("Frosinone")
                        .build())))
                .build());
        data.add(Ticket.builder().date(new Date(System.currentTimeMillis()))
                .totalOdds(55.63)
                .matchList(new ArrayList<>(Arrays.asList(Match.builder()
                        .name("Fregamo")
                        .build())))
                .build());


        return new ListDataProvider<>(data);
    }

}