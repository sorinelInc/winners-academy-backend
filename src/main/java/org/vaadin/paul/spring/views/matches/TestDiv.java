package org.vaadin.paul.spring.views.matches;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.paul.spring.model.Person;
import org.vaadin.paul.spring.model.Result;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class TestDiv extends VerticalLayout {

    private Grid<Person> grid = new Grid<>();

    private Button refresh = new Button("Refresh");

    public TestDiv() {

        refresh.setVisible(false);
        refresh.addClickListener(buttonClickEvent -> {
            this.grid.setItems(getPeople());
        });

        grid.setHeightByRows(true);
        grid.setWidth("800px");

        grid.addColumn(Person::getName).setWidth("200px").setHeader("Match");
        grid.addColumn(Person::getTips).setHeader("Tip");
        grid.addColumn(Person::getDate).setHeader("Date");
        grid.addColumn(person -> Double.toString(person.getOdds())).setHeader("Odds");
        grid.addColumn(Person::getResult).setHeader("Result");

        Label totalOdds = new Label("Total Odds:");
        totalOdds.add();
        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, totalOdds);

        Label result = new Label("Result: ");
        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, result);

        add(refresh, grid, totalOdds, result);
    }

    public void update() {
        refresh.click();
    }

    private List<Person> getPeople() {
        return Arrays.asList(
                new Person("Nicolaus Copernicus", "1X", Result.WON, new Date(System.currentTimeMillis()), 3.45D),
                new Person("Asdsaasd sada", "1", Result.LOST, new Date(System.currentTimeMillis()), 1.25D),
                new Person("sds Sdsd", "Over 2.5", Result.WON, new Date(System.currentTimeMillis()), 1.45D)
        );
    }
}
