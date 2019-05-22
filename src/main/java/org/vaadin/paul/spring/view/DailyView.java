package org.vaadin.paul.spring.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.paul.spring.repository.TicketRepository;
import org.vaadin.paul.spring.utils.Routes;
import org.vaadin.paul.spring.view.matches.FirstPage;
import org.vaadin.paul.spring.view.matches.SecondPage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route(value = Routes.DAILY, layout = MainView.class)
@PageTitle("Daily Matches")
public class DailyView extends VerticalLayout {

    private TicketRepository ticketRepository;

    @Autowired
    public DailyView(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
        VerticalLayout layout = new VerticalLayout();
        LocalDate today = LocalDate.now();

        Tab tab1 = new Tab(today.toString());
        Tab tab2 = new Tab(today.minusDays(1).toString());
        Tab tab3 = new Tab(today.minusDays(2).toString());
        Tab tab4 = new Tab(today.minusDays(3).toString());
        Tab tab5 = new Tab(today.minusDays(4).toString());
        Tab tab6 = new Tab(today.minusDays(5).toString());
        Tab tab7 = new Tab(today.minusDays(6).toString());

        FirstPage page1 = new FirstPage(ticketRepository);
        page1.setVisible(true);

//        SecondPage page2 = new SecondPage(ticketRepository);
        Div page2 = new Div();
        page2.setText("Page#4");
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

        Set<Component> pagesShown = Stream.of(page1).collect(Collectors.toSet());
        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            if (selectedPage.equals(page2)) {
//                page2.update();
            }
            else if(selectedPage.equals(page1)){
//                page1.update();
            }
            pagesShown.add(selectedPage);
        });

        setHorizontalComponentAlignment(Alignment.CENTER, tabs, pages, layout);

        layout.add();
        layout.add(tabs, pages);
        add(layout);
    }
}