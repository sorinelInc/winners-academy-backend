package org.vaadin.paul.spring.view;


import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.paul.spring.utils.Routes;

@Route(value = Routes.CONTACT, layout = MainView.class)
@PageTitle("Contact")
public class ContactView extends FormLayout {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");

    public ContactView() {
        add(firstName, lastName);
    }

}
