package org.vaadin.paul.spring.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.paul.spring.utils.Routes;

@Route(value = Routes.USERS, layout = MainView.class)
@PageTitle("Users")
public class UsersView extends VerticalLayout {
}
