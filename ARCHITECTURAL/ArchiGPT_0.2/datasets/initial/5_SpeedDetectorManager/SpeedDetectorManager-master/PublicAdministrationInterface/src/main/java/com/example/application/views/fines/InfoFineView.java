package com.example.application.views.fines;



//import com.example.application.data.entity.User;
import com.example.application.data.service.AuthService;
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

import java.io.*;


@PageTitle("Login | Pubblica amministrazione Repubblica Italiana")
public class InfoFineView extends Div {
    private Span span;
    private StringBuilder text;
    private Anchor download;
    public InfoFineView(AuthService auth)  {

        span = new Span();
        text = new StringBuilder();
        download = new Anchor();
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        Fine fine = VaadinSession.getCurrent().getAttribute(Fine.class);

        if(fine==null) {
            //questo è per gestire il caso in cui una persona dopo aver fatto l'accesso acceda a "multe/info" senza scegliere una multa
            //UI.getCurrent().getPage().setLocation(AuthService.newFinesRoute);
            text.append( "<h3>Nessuna multa selezionata, informazioni non disponibili.</h3>\n" );
            span.getElement().setProperty("innerHTML",text.toString());
            VerticalLayout output = new VerticalLayout(span);
            add(output);
            return;
        }
        Car car = fine.getCar();
        Person p = fine.getReceiver();
        //MODIFICARE IL TEST IN BASE ALLE PROPRIE ESIGENZE
        text.append( "<h3>Pubblica amministrazione Italiana - Regione " + user.getRegion()  +"</h3>\n" );

        text.append("<p>Il veicolo targato " + car.getLicensePlate() + ", intestato a " + p.getName() + " " + p.getSurname() + ", ha ricevuto la seguente multa: </p>\n");
        text.append("<p>Residenza intestatario del veicolo: " + p.getAddress() + " </p>\n\n\n");
        text.append("<p>Regione di competenza: " + fine.getSpeedCameraRegion() + " </p>\n");
        text.append( "<p>Identificatore dell'autovelox: " + fine.getSpeedCameraId() + " </p>\n\n\n" );
        text.append( "<p>Data della sanzione: " + fine.getStringDate() + " </p>\n\n\n" );
        text.append( "<p>Ammontare della multa: " + fine.getAmount() + " </p>\n" );
        text.append( "<p>Punti sottratti dalla patente: " + fine.getPoints() + " </p>\n\n\n" );
        if(fine.getUser() != null)
            text.append( "<p>Responsabile della sanzione: " + fine.getUser().getUsername() + " </p>\n" );

        //MODIFICARE IL TESTO IN BASE ALLE PROPRIE ESIGENZE
        span.getElement().setProperty("innerHTML",text.toString());

        Button downloadButton = new Button("Scarica PDF", event -> {
            try{
                downloadPDF(text.toString());
            }
            catch(FileNotFoundException e){
                System.out.println(e);
            }
            catch(IOException e){
                System.out.println(e);
            }
        });
        VerticalLayout output = new VerticalLayout(
                span,
                downloadButton
        );
        add(output);

    }
    private void downloadPDF(String HTML) throws FileNotFoundException, IOException {
        String username = VaadinSession.getCurrent().getAttribute(User.class).getUsername();
        String fineId = VaadinSession.getCurrent().getAttribute(Fine.class).getId().toString();

        String resultFileName = "Multa_"+fineId+"-Utente_"+username+".pdf";
        HtmlConverter.convertToPdf(HTML, new FileOutputStream(resultFileName));


        System.out.println( "PDF Created!" );
        File file = new File(resultFileName);
        final StreamResource resource = getStreamResource(resultFileName, file);
        final StreamRegistration registration = VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);
        //UI.getCurrent().getPage().setLocation(registration.getResourceUri());
        UI.getCurrent().getPage().open(registration.getResourceUri().toString());

    }
    public static StreamResource getStreamResource(String fileName, File file) {
        return new StreamResource(fileName, () -> {
            try {
                return new FileInputStream(file);
            } catch (IOException e) {
                System.out.println("Error in getStreamResource: ");
                e.printStackTrace();
            }
            return null;
        });
    }
}