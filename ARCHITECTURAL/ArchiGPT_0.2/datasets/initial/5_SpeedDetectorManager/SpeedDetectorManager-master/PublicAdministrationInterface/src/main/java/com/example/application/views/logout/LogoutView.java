package com.example.application.views.logout;

import com.example.application.views.Paths;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;

//@Route("logout")
@PageTitle(Paths.logoutRoute)
public class LogoutView extends Composite<VerticalLayout> {

    public LogoutView() {
        UI.getCurrent().getPage().setLocation("login");
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
    }

}
