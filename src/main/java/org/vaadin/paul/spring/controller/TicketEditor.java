package org.vaadin.paul.spring.controller;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.vaadin.paul.spring.dialog.EditTicketDialog;
import org.vaadin.paul.spring.model.Match;
import org.vaadin.paul.spring.model.Ticket;
import org.vaadin.paul.spring.repository.TicketRepository;

import java.util.Set;

@Controller
public class TicketEditor extends VerticalLayout implements KeyNotifier {

    private final TicketRepository repository;

    private Ticket currentTicket;

    private Grid<Match> matchGrid = new Grid<>(Match.class);

    private Button addMatch = new Button("Add Match", VaadinIcon.PLUS.create());
    private Button deleteMatch = new Button("Delete Match", VaadinIcon.TRASH.create());
    private Button editMatch = new Button("Edit Match", VaadinIcon.EDIT.create());
    private Button cancel = new Button("Cancel");
    private HorizontalLayout actions = new HorizontalLayout(addMatch, editMatch, deleteMatch, cancel);

    private Binder<Ticket> binder = new Binder<>(Ticket.class);
    private ChangeHandler changeHandler;

    @Autowired
    public TicketEditor(TicketRepository repository) {
        this.repository = repository;
        matchGrid.removeColumnByKey("parentTicket");
        add(matchGrid, actions);
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

        addMatch.getElement().getThemeList().add("primary");
        deleteMatch.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        EditTicketDialog editTicketDialog = new EditTicketDialog();

        // wire action buttons to addMatch, deleteMatch and reset
        addMatch.addClickListener(event -> save());
        deleteMatch.addClickListener(event -> delete());
        cancel.addClickListener(event -> cancel());
        editMatch.addClickListener(event -> editTicketDialog.open());

        setVisible(false);
    }

    private void delete() {
        repository.delete(currentTicket);
        changeHandler.onChange();
    }

    private void save() {
        repository.save(currentTicket);
        changeHandler.onChange();
    }

    private void cancel() {
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
//        date.focus();
    }

    public void deleteTicket(Set<Ticket> ticketSet){
        if (ticketSet.isEmpty()){
            //TODO: popup
        }
        else {
            Ticket currentTicket = ticketSet.iterator().next();
            repository.delete(currentTicket);
            changeHandler.onChange();
        }
    }


    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either addMatch or deleteMatch
        // is clicked
        changeHandler = h;
    }

}
