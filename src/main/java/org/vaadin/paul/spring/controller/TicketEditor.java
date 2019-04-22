package org.vaadin.paul.spring.controller;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.vaadin.paul.spring.model.Ticket;
import org.vaadin.paul.spring.repository.TicketRepository;

@Controller
public class TicketEditor extends VerticalLayout implements KeyNotifier {

    private final TicketRepository repository;

    private Ticket customer;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");


    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Ticket> binder = new Binder<>(Ticket.class);
    private ChangeHandler changeHandler;

    @Autowired
    public TicketEditor(TicketRepository repository) {
        this.repository = repository;

        add(firstName, lastName, actions);

        // bind using naming convention
//        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCustomer(customer));
        setVisible(false);
    }

    void delete() {
        repository.delete(customer);
        changeHandler.onChange();
    }

    void save() {
        repository.save(customer);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editCustomer(Ticket c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getTicketId() != null;
        if (persisted) {
            // Find fresh entity for editing
            customer = repository.findById(c.getTicketId()).get();
        }
        else {
            customer = c;
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(customer);

        setVisible(true);

        // Focus first name initially
        firstName.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }
}
