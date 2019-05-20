package org.vaadin.paul.spring.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainView.class)
@PageTitle("Home")
public class HomeView extends VerticalLayout {

}
