package com.example.application.views.fines;

//import com.example.application.data.entity.Fine;
//import com.example.application.data.entity.User;
import com.example.application.data.service.AuthService;
//import com.example.application.data.service.FineRepository;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import it.uniroma1.commons.entity.Fine;
import it.uniroma1.commons.entity.User;
import it.uniroma1.commons.repository.FineRepository;
import it.uniroma1.commons.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


//import org.springframework.beans.factory.annotation.Autowired;

//@Route(value = "multe/gestite", layout = FinesView.class)
//@Service



public class GestiteView extends VerticalLayout {
    // @Autowired
    private final FineRepository fineRepository;
    Grid<Fine> grid = new Grid<>(Fine.class);
    TextField filterText = new TextField();

    public GestiteView(FineRepository fineRep) {
        fineRepository=fineRep;

        addClassName("nuove-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(), grid);

        this.refreshGrid();
    }


    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.removeAllColumns();

        grid.addColumn(fine -> fine.getId()).setHeader("Codice");
        grid.addColumn(fine -> fine.getSpeedCameraId()).setHeader("Autovelox");
        grid.addColumn(fine -> fine.getReceiverFiscalCode()).setHeader("Destinatario");
        grid.addColumn(fine -> fine.getStringDate()).setHeader("Data");
        grid.addColumn(fine -> fine.getUser().getUsername()).setHeader("Responsabile");

        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, fine) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_TERTIARY
                    );

                    button.addClickListener(e ->{
                        VaadinSession.getCurrent().setAttribute(Fine.class,fine);
                        UI.getCurrent().getPage().setLocation("multe/info");
                    } );
                    button.setIcon(new Icon(VaadinIcon.INFO));
                })).setHeader("Dettagli");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }




    public void refreshGrid(String filter){
        if(filter.equals(""))
            grid.setItems(fineRepository.findAllManagedFines(VaadinSession.getCurrent().getAttribute(User.class).getRegion()));
        else{
            if(! StringUtils.isNumeric(filter) || Integer.parseInt(filter)<0)
                Notification.show("L'id di un autovelox è un intero positivo");

            else
                grid.setItems(fineRepository.findFilterAllManagedFines(VaadinSession.getCurrent().getAttribute(User.class).getRegion(), Integer.parseInt(filter)));

        }
    }
    public void refreshGrid(){
        refreshGrid(filterText.getValue());
    }
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filtra per autovelox...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);


        Button updateButton = new Button("Aggiorna", event -> refreshGrid());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, updateButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}
