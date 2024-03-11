package com.example.application.views.admin;


//import com.example.application.data.entity.Fine;
//import com.example.application.data.entity.User;
//import com.example.application.data.service.FineRepository;
import com.example.application.views.Paths;
        import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
        import com.vaadin.flow.server.VaadinSession;
import it.uniroma1.commons.entity.Fine;
import it.uniroma1.commons.entity.User;
import it.uniroma1.commons.repository.FineRepository;
        import org.apache.commons.lang3.StringUtils;


//import org.springframework.beans.factory.annotation.Autowired;

//@Route(value = "multe/gestite", layout = FinesView.class)
//@Service



public class UserInfoView extends VerticalLayout {
    public static String userAnalyzed = "userAnalyzed";
    // @Autowired
    private final FineRepository fineRepository;
    private Grid<Fine> grid = new Grid<>(Fine.class);
    private TextField filterText = new TextField();

    private Span span; //utilizzato solo nel caso di utente non selezionato
    private User analyzedUser = (User)VaadinSession.getCurrent().getAttribute(userAnalyzed);
    private StringBuilder text;//utilizzato solo nel caso di utente non selezionato
    public UserInfoView(FineRepository fineRep) {
        fineRepository=fineRep;
        span = new Span();
        text = new StringBuilder();
        if(analyzedUser==null) {
            //questo è per gestire il caso in cui una persona dopo aver fatto l'accesso acceda a "utenti/info"
            // senza scegliere un utente
            //UI.getCurrent().getPage().setLocation(AuthService.adminUsersRoute);
            text.append( "<h3>Nessun utente selezionato, informazioni non disponibili.</h3>\n" );
            span.getElement().setProperty("innerHTML",text.toString());
            VerticalLayout output = new VerticalLayout(span);
            add(output);
            return;
        }


        addClassName("userInfo-view");
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
        //grid.addColumn(fine -> fine.getDate()).setHeader("Data");
        grid.addColumn(fine -> fine.getStringDate()).setHeader("Data");

        grid.addColumn(fine -> fine.getUser().getUsername()).setHeader("Responsabile");

        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, fine) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_TERTIARY
                    );

                    button.addClickListener(e ->{
                        VaadinSession.getCurrent().setAttribute(Fine.class,fine);
                        UI.getCurrent().getPage().setLocation(Paths.adminUsersFineRoute);
                    } );
                    button.setIcon(new Icon(VaadinIcon.INFO));
                })).setHeader("Dettagli");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }




    public void refreshGrid(String filter){

        if(filter.equals(""))
            grid.setItems(fineRepository.findAllManagedFinesByManager(analyzedUser.getUsername()));
        else{
            if(! StringUtils.isNumeric(filter) || Integer.parseInt(filter)<0)
                Notification.show("L'id di un autovelox è un intero positivo");

            else
                grid.setItems(fineRepository.findFilterAllManagedFinesByManager(Integer.parseInt(filter),analyzedUser.getUsername()));
            //System.out.println(analyzedUser.getUsername());
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
