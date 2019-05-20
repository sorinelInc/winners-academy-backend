package org.vaadin.paul.spring.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "vip",  layout = MainView.class)
@PageTitle("VIP Matches")
public class VIPView  extends VerticalLayout {
}
