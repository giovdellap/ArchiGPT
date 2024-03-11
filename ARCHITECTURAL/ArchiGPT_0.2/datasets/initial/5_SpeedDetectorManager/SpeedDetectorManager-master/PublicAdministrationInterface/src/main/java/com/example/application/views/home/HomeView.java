package com.example.application.views.home;



//import com.example.application.data.entity.User;
import com.example.application.data.service.AuthService;
import com.example.application.views.Paths;
import com.example.application.views.admin.UserInfoView;
import com.itextpdf.html2pdf.HtmlConverter;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import it.uniroma1.commons.entity.Car;
import it.uniroma1.commons.entity.Fine;
import it.uniroma1.commons.entity.Person;
import it.uniroma1.commons.entity.User;
import it.uniroma1.commons.enums.Role;

import java.io.*;


@PageTitle("Login | Pubblica amministrazione Repubblica Italiana")
public class HomeView extends Div {
    private Span span;
    private StringBuilder text;

    public HomeView(AuthService auth)  {

        span = new Span();
        text = new StringBuilder();
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        //MODIFICARE IL TEST IN BASE ALLE PROPRIE ESIGENZE
        text.append( "<h3>Dati Utente " + user.getUsername()  +"</h3>\n" );

        text.append( "<p>Nome utente: "+user.getUsername()+"</p\n" );
        text.append( "<p>Nome: " + user.getName() + " </p>\n" );
        text.append( "<p>Cognome: " + user.getSurname() + " </p>\n\n\n" );
        text.append( "<p>Regione di competenza: " + user.getRegion() + " </p>\n\n\n" );
        text.append( "<p>Ruolo: " + user.getRole().toString() + " </p>\n" );

        span.getElement().setProperty("innerHTML",text.toString());


        Button infoButton;
        /*
        if(user.getRole()== Role.USER){
            infoButton = new Button("Multe gestite", event -> {
                VaadinSession.getCurrent().setAttribute(UserInfoView.userAnalyzed, user);
                UI.getCurrent().getPage().setLocation(Paths.userInfoFinesRoute);
            });
            output = new VerticalLayout(
                    span
            );
        }
        else{
            /*in
            foButton = new Button("Utenti creati", event -> {
                //VaadinSession.getCurrent().setAttribute(UserInfoView.userAnalyzed, user);
                UI.getCurrent().getPage().setLocation(AuthService.adminInfoUsersRoute);
            });
             output = new VerticalLayout(
                    span
            );
        }
        */

        VerticalLayout output = new VerticalLayout(
                span
        );


        add(output);

    }

}