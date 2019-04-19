package org.vaadin.paul.spring.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "company")
@PageTitle("Company")
public class AnotherView extends VerticalLayout {

    private final Button addNewBtn;

    public AnotherView(){
        this.addNewBtn = new Button("New employee",VaadinIcon.PLUS.create());
        add(addNewBtn);
    }

}
