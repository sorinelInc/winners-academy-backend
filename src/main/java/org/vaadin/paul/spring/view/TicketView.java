package org.vaadin.paul.spring.view;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.paul.spring.controller.TicketEditor;
import org.vaadin.paul.spring.model.Ticket;
import org.vaadin.paul.spring.repository.TicketRepository;
import org.vaadin.paul.spring.utils.Routes;

@Route(value = Routes.TICKET, layout = MainView.class)
@PageTitle("Tickets")

public class TicketView extends VerticalLayout {
//public class TicketView extends VerticalLayout {

    private final TicketRepository ticketRepository;

    private final TicketEditor editor;

    final Grid<Ticket> grid;

    final TextField filter;

    private final Button addNewBtn;

    private final Button deleteNewBtn;


    @Autowired
    public TicketView(TicketRepository ticketRepository, TicketEditor editor) {
        this.ticketRepository = ticketRepository;
        this.editor = editor;
        grid = new Grid<>(Ticket.class);
        filter = new TextField();
        addNewBtn = new Button("New Ticket", VaadinIcon.PLUS.create());
        deleteNewBtn = new Button("Delete Ticket", VaadinIcon.TRASH.create());
        deleteNewBtn.getElement().getThemeList().add("error");

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, deleteNewBtn);
        add(actions, grid, editor);

//        grid.setHeight("300px");
        grid.setColumns("ticketId", "date", "totalOdds", "status");
        grid.getColumnByKey("ticketId").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filter ticket date");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listMatches(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editTicket(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editTicket(new Ticket()));
        deleteNewBtn.addClickListener(e -> editor.deleteTicket(grid.getSelectedItems()));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listMatches(filter.getValue());
        });

        // Initialize listing
        listMatches(null);

    }


    void listMatches(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(ticketRepository.findAll());
        } else {
            grid.setItems(ticketRepository.findAll());
        }
    }
}
