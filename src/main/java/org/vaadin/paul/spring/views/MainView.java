package org.vaadin.paul.spring.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.vaadin.paul.spring.MessageBean;
import org.vaadin.paul.spring.security.SecurityUtils;

@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends AbstractAppRouterLayout {

    public MainView(@Autowired MessageBean bean) {

    }


    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu appLayoutMenu) {
        appLayout.setBranding(new Span("Winners Bet"));

        setMenuItem(appLayoutMenu, new AppLayoutMenuItem(VaadinIcon.HOME.create(), "Home", "home"));
        setMenuItem(appLayoutMenu, new AppLayoutMenuItem(VaadinIcon.WALLET.create(), "Daily Matches", "daily"));
        setMenuItem(appLayoutMenu, new AppLayoutMenuItem(VaadinIcon.STAR.create(), "VIP Matches", "vip"));
        setMenuItem(appLayoutMenu, new AppLayoutMenuItem(VaadinIcon.CONNECT.create(), "Contact", "contact"));

        if (SecurityUtils.isAccessGranted(MatchesView.class)) {
            setMenuItem(appLayoutMenu, new AppLayoutMenuItem(VaadinIcon.EDIT.create(), "Matches", "company"));
        }

    }


    private void setMenuItem(AppLayoutMenu menu, AppLayoutMenuItem menuItem) {
        menuItem.getElement().setAttribute("theme", "icon-on-top");
        menu.addMenuItem(menuItem);
    }
}
