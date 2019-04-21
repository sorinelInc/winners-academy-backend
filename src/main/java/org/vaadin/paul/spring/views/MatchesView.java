package org.vaadin.paul.spring.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudI18n;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.paul.spring.model.Match;
import org.vaadin.paul.spring.model.Ticket;
import org.vaadin.paul.spring.utils.Role;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Route("company")
@Secured(Role.ADMIN)
public class MatchesView extends VerticalLayout {

    public MatchesView() {
        setSizeFull();
        ListDataProvider<Ticket> dataProvider = createTicketDataProvider();
        Grid<Ticket> ticketGrid = createTicketGrid();
        ticketGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        Crud<Ticket> crud = new Crud<>(Ticket.class, ticketGrid, createTicketEditor(ticketGrid));
        crud.setMaxWidth("800px");
        crud.setWidth("100%");
        crud.setDataProvider(dataProvider);
        setHorizontalComponentAlignment(Alignment.CENTER, crud);

        crud.addSaveListener(saveEvent -> {
            Ticket toSave = saveEvent.getItem();
             if (!dataProvider.getItems().contains(toSave)) {
                dataProvider.getItems().add(toSave);
            }
        });

        crud.addDeleteListener(deleteEvent -> {
            dataProvider.getItems().remove(deleteEvent.getItem());
        });

        add(crud);
    }


    /* Hibernate ??? */
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

    private ListDataProvider<Match> createMatchDataProvider(Grid<Ticket> parentTicket) {
        List<Match> data = new ArrayList<>();
        parentTicket.getSelectionModel().getFirstSelectedItem().ifPresent(selectedTicket -> data.addAll(selectedTicket.getMatchList()));
        return new ListDataProvider<>(data);
    }

    private Grid<Ticket> createTicketGrid() {
        Grid<Ticket> grid = new Grid<>();
        grid.addColumn(Ticket::getDate).setHeader("Ticket Date")
                .setWidth("160px");
        grid.addColumn(Ticket::getTotalOdds).setHeader("Total Odds");
        Crud.addEditColumn(grid);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        return grid;
    }

    private Grid<Match> createMatchGrid() {
        Grid<Match> grid = new Grid<>();
        grid.addColumn(Match::getName).setHeader("Match")
                .setWidth("160px");
        grid.addColumn(Match::getTips).setHeader("Tips");
        Crud.addEditColumn(grid);
        return grid;
    }


    private CrudEditor<Ticket> createTicketEditor(Grid<Ticket> ticketGrid) {
//        TextField companyName = new TextField("Company name");
//        companyName.setRequiredIndicatorVisible(true);
//        setColspan(companyName, 4);
//        TextField contactName = new TextField("Contact name");
//        contactName.setRequiredIndicatorVisible(true);
//        setColspan(contactName, 2);
//        EmailField contactEmail = new EmailField("Contact email");
//        setColspan(contactEmail, 2);
//        contactEmail.setRequiredIndicatorVisible(true);
//
//        TextField address = new TextField("Address");
//        setColspan(address, 2);
//
//        TextField city = new TextField("City");
//        TextField zip = new TextField("Postal/Zip code");
//
//        TextField region = new TextField("Region");
//        setColspan(region, 2);
//
//        ComboBox<String> country = new ComboBox<>();
//        country.setAllowCustomValue(true);
//        country.setLabel("Country");
//        setColspan(country, 2);
//
//        country.setItems(getCountriesList());
//        TextField phone = new TextField("Phone");
//        setColspan(phone, 2);
//        phone.setRequiredIndicatorVisible(true);
//        TextField fax = new TextField("Fax");
//        setColspan(fax, 2);
//
//        FormLayout form = new FormLayout(companyName, contactName, contactEmail,
//                address, city, zip, region, country, phone, fax);
//        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
//
//        Binder<Company> binder = new Binder<>(Company.class);
//        binder.bind(companyName, Company::getCompanyName, Company::setCompanyName);
//        binder.bind(contactName, Company::getContactName, Company::setContactName);
//        binder.bind(contactEmail, Company::getContactEmail, Company::setContactEmail);
//        binder.bind(address, Company::getAddress, Company::setAddress);
//        binder.bind(city, Company::getCity, Company::setCity);
//        binder.bind(zip, Company::getZip, Company::setZip);
//        binder.bind(region, Company::getRegion, Company::setRegion);
//        binder.bind(country, Company::getCountry, Company::setCountry);
//        binder.bind(phone, Company::getPhone, Company::setPhone);
//        binder.bind(fax, Company::getFax, Company::setFax);



//        Grid<Match> matchGrid = createMatchGrid();
//        Crud<Match> matchCrud = new Crud<>(Match.class, matchGrid, createMatchEditor());
//        matchCrud.setMaxWidth("800px");
//        matchCrud.setWidth("100%");
//        matchCrud.getGrid().removeColumnByKey("ticketId");
//        matchCrud.getGrid().setSortableColumns();




//        matchCrud.setDataProvider(createMatchDataProvider(ticketGrid));


        TextField ticketDate = new TextField("Ticket Date");
        ticketDate.setRequiredIndicatorVisible(true);
        ticketDate.setRequired(true);
        setColspan(ticketDate, 2);

        TextField ticketOdds = new TextField("Ticket Odds");
        ticketOdds.setReadOnly(true);
        setColspan(ticketDate, 2);


        Binder<Ticket> binder = new Binder<>(Ticket.class);

        FormLayout form = new FormLayout(ticketDate, ticketOdds);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));

        return new BinderCrudEditor<>(binder, form);
    }


    private CrudEditor<Match> createMatchEditor() {
        TextField matchName = new TextField("Match");
        matchName.setRequiredIndicatorVisible(true);
        matchName.setRequired(true);
        setColspan(matchName, 2);

        TextField tips = new TextField("Tips");
        tips.setRequiredIndicatorVisible(true);
        tips.setRequired(true);
        setColspan(tips, 2);

        FormLayout form = new FormLayout(matchName, tips);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));

        Binder<Match> binder = new Binder<>(Match.class);
        binder.bind(matchName, Match::getName, Match::setName);
        binder.bind(tips, Match::getTips, Match::setTips);

        return new BinderCrudEditor<>(binder, form);
    }

    private void setColspan(Component component, int colspan) {
        component.getElement().setAttribute("colspan", Integer.toString(colspan));
    }
}