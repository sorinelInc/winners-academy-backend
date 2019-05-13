package org.vaadin.paul.spring.controller;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.vaadin.paul.spring.model.Match;
import org.vaadin.paul.spring.model.Ticket;
import org.vaadin.paul.spring.repository.TicketRepository;

import java.time.LocalDate;

@Controller
public class TicketEditor extends VerticalLayout implements KeyNotifier {

    private final TicketRepository repository;

    private Ticket currentTicket;

    TextField id = new TextField("Match Id");
    TextField date = new TextField("Date");
//    DatePicker datePicker = new DatePicker();
    Grid<Match> matchGrid = new Grid<>(Match.class);

    TextField totalOdds = new TextField("Total Odds");


    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Ticket> binder = new Binder<>(Ticket.class);
    private ChangeHandler changeHandler;

    @Autowired
    public TicketEditor(TicketRepository repository) {
        this.repository = repository;

        add(id, matchGrid, totalOdds, matchGrid, actions);

        // bind using naming convention
//        binder.forMemberField(date)
//                .withConverter(str -> LocalDate.now(), date -> LocalDate.now())
//                .bind(Ticket::getDate, Ticket::setDate);
//        binder.forMemberField(id)
//                .withConverter(str -> str, date -> date);
//        binder.forField(totalOdds).bind(Ticket::getStatus, Ticket::setStatus);

//        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editTicket(currentTicket));
        setVisible(false);
    }

    void delete() {
        repository.delete(currentTicket);
        changeHandler.onChange();
    }

    void save() {
        repository.save(currentTicket);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editTicket(Ticket ticket) {
        if (ticket == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = ticket.getTicketId() != null;
        if (persisted) {
            // Find fresh entity for editing
            currentTicket = repository.findById(ticket.getTicketId()).get();
        }
        else {
            currentTicket = ticket;
        }
        cancel.setVisible(persisted);

        // Bind currentTicket properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving

        matchGrid.setItems(currentTicket.getMatchList());
        binder.setBean(currentTicket);


        setVisible(true);

        // Focus first name initially
        date.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }
}
