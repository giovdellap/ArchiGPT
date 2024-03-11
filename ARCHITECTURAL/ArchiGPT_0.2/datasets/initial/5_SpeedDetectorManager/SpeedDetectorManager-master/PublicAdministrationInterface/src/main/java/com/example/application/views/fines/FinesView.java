package com.example.application.views.fines;

//import com.example.application.data.entity.User;
import com.example.application.views.home.HomeView;
import com.example.application.views.logout.LogoutView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import it.uniroma1.commons.entity.User;

//@Route(value = "multe")


@PageTitle("Multe | Pubblica amministrazione Repubblica Italiana")
public class FinesView extends AppLayout {

    private User user;

    public FinesView() {
        user = VaadinSession.getCurrent().getAttribute(User.class);
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        String regione;
        if(user == null)
            regione = "UTENTE NULL";
        else
            regione = user.getRegion();
        H1 logo = new H1("Gestione multe della regione " + regione);
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);


        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        addToDrawer(new VerticalLayout(new RouterLink("Nuove", NuoveView.class), new RouterLink("Gestite", GestiteView.class),new RouterLink("Account", HomeView.class), new RouterLink("Logout", LogoutView.class)));
    }
}

