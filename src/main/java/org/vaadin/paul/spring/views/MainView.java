package org.vaadin.paul.spring.views;

import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftNavigationItem;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;


public class MainView extends AppLayoutRouterLayout {

    public MainView() {
        init(AppLayoutBuilder
                .get(Behaviour.LEFT_RESPONSIVE_HYBRID)
                .withTitle(new H1("Winners bet"))
                .withAppMenu(LeftAppMenuBuilder.get()
                        .add(new LeftNavigationItem("Home", VaadinIcon.HOME.create(), HomeView.class))
                        .add(new LeftNavigationItem("Daily Matches", VaadinIcon.WALLET.create(), DailyView.class))
                        .add(new LeftNavigationItem("VIP Matches", VaadinIcon.STAR.create(), VIPView.class))
                        .add(new LeftNavigationItem("Register", VaadinIcon.PLUS.create(), PaymentView.class))
                        .add(new LeftNavigationItem("Contact", VaadinIcon.CONNECT.create(), DailyView.class))
                        .build())
                .build());
    }
}

