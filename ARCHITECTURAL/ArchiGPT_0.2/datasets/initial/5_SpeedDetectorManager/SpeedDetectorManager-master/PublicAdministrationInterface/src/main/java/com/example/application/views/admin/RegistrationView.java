package com.example.application.views.admin;

import com.example.application.data.service.AuthService;
import com.example.application.views.logout.LogoutView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import it.uniroma1.commons.entity.User;
import it.uniroma1.commons.enums.Role;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;





@PageTitle("Admin | Pubblica amministrazione Repubblica Italiana")
public class RegistrationView extends Div {

    public RegistrationView(AuthService auth){
        TextField username = new TextField("Nome Utente");
        TextField name = new TextField("Nome");
        TextField surname = new TextField("Cognome");

        PasswordField password1 = new PasswordField("Password");
        PasswordField password2 = new PasswordField("Conferma Password");
        ComboBox<String> selectRegion = new ComboBox<>();
        selectRegion.setPlaceholder("Seleziona regione");
        selectRegion.setItems("Abruzzo", "Basilicata", "Calabria", "Campania",
                "Emilia Romagna", "Friuli-Venezia Giulia", "Lazio", "Liguria", "Lombardia", "Marche", "Molise", "Piemonte",
                "Puglia", "Sardegna", "Sicilia", "Toscana", "Trentino-Alto Adige", "Umbria", "Val d'Aosta", "Veneto");

        ComboBox<String> selectRole = new ComboBox<>();
        selectRole.setPlaceholder("Seleziona ruolo");
        selectRole.setItems("User","Admin");


        addClassName("admin-view");
        //setSizeFull();

        VerticalLayout output = new VerticalLayout(
                //new H1("Pubblica amministrazione Repubblica Italiana"),
                new H2("Registra nuovo utente"),
                username,
                new HorizontalLayout(name, surname),
                new HorizontalLayout(selectRegion, selectRole),
                new HorizontalLayout(password1, password2),

                //new HorizontalLayout(select, selectRegion),
                new Button("Registra", event -> {
                    try {
                        if(! password1.getValue().equals(password2.getValue()))
                            Notification.show("Password differenti.");
                        else{

                            Role role = selectRole.getValue().equals("User")?Role.USER:Role.ADMIN;

                            auth.register(username.getValue(),name.getValue(),surname.getValue(),password1.getValue(),selectRegion.getValue(), role);
                            Notification.show("Utente "+username.getValue()+" con regione "+selectRegion.getValue()+" creato.");
                            username.setValue("");
                            name.setValue("");
                            surname.setValue("");
                            password1.setValue("");
                            password2.setValue("");
                            selectRole.setValue(null);
                            selectRegion.setValue(null);


                        }




                    } catch (Exception e) {
                        Notification.show("Problemi nella creazione dell'utente");

                    }
                }),
                new RouterLink("Logout", LogoutView.class)
        );
        output.setAlignItems(FlexComponent.Alignment.CENTER);
        output.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        add(output);
    }
}