package com.example.application.views.login;

//import com.example.application.data.entity.User;
import com.example.application.data.service.AuthService;
import com.example.application.views.Paths;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import it.uniroma1.commons.entity.User;

@Route("login")


@PageTitle("Login | Pubblica amministrazione Repubblica Italiana")
public class LoginView extends Div {

    public LoginView(AuthService auth){
        TextField user = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        ComboBox<String> select = new ComboBox<>();
        select.setPlaceholder("Seleziona regione");

        select.setItems("Abruzzo", "Basilicata", "Calabria", "Campania",
                "Emilia Romagna", "Friuli-Venezia Giulia", "Lazio", "Liguria", "Lombardia", "Marche", "Molise", "Piemonte",
                "Puglia", "Sardegna", "Sicilia", "Toscana", "Trentino-Alto Adige", "Umbria", "Val d'Aosta", "Veneto");


        addClassName("login-view");
        //setSizeFull();

        VerticalLayout output = new VerticalLayout(
                new H1("Pubblica amministrazione Repubblica Italiana"),
                user,
                password,
                select,
                new Button("Login", event -> {
                    try {
                        User user_current = VaadinSession.getCurrent().getAttribute(User.class);
                        if(user_current==null) {
                            auth.authenticate(user.getValue().strip(), password.getValue().strip(), select.getValue());
                        }
                        else {
                            UI.getCurrent().navigate(Paths.logoutRoute);
                        }

                    } catch (AuthService.AuthException e) {
                        Notification.show("Credenziali errate.");
                    }})
        );
        output.setAlignItems(FlexComponent.Alignment.CENTER);
        output.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        add(output);
    }
}