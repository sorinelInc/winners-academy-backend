package org.vaadin.paul.spring.views;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.paul.spring.utils.Routes;

@Tag("sa-login-view")
@Route(value = Routes.LOGIN)
@PageTitle("Login")
public class LoginView extends VerticalLayout {

    public LoginView() {

        LoginOverlay login = new LoginOverlay();

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setAdditionalInformation("Extra info here");

        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);
        login.setOpened(true);
        login.setTitle("Winners Academy");

        login.setDescription("Login to VIP section");
        getElement().appendChild(login.getElement());

        login.setI18n(i18n);
    }
}
