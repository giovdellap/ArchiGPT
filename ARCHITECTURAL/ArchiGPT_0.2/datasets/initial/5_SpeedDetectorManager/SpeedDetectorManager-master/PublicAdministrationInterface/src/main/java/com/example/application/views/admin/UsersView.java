package com.example.application.views.admin;

//import com.example.application.data.entity.Fine;
//import com.example.application.data.entity.User;
//import com.example.application.data.service.FineRepository;
import com.example.application.views.Paths;
        import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
        import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
        import com.vaadin.flow.server.VaadinSession;
        import it.uniroma1.commons.entity.User;
        import it.uniroma1.commons.repository.UserRepository;


//import org.springframework.beans.factory.annotation.Autowired;

//@Route(value = "multe/gestite", layout = FinesView.class)
//@Service



public class UsersView extends VerticalLayout {
    // @Autowired
    private final UserRepository userRepository;
    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();

    public UsersView(UserRepository userRep) {
        userRepository=userRep;

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

        grid.addColumn(user -> user.getUsername()).setHeader("Username");
        grid.addColumn(user -> user.getName()+" "+user.getSurname()).setHeader("Impiegato");
        grid.addColumn(user -> user.getRegion()).setHeader("Regione");

        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, user) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_TERTIARY
                    );

                    button.addClickListener(e ->{
                        VaadinSession.getCurrent().setAttribute("userAnalyzed",user);
                        UI.getCurrent().getPage().setLocation(Paths.adminUsersInfoRoute);
                    } );
                    button.setIcon(new Icon(VaadinIcon.INFO));
                })).setHeader("Dettagli");



        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }




    public void refreshGrid(String filter){
        if(filter.equals(""))
            grid.setItems(userRepository.findAll());

        else
            grid.setItems(userRepository.findFilterAll(filter));
    }
    public void refreshGrid(){
        refreshGrid(filterText.getValue());
    }
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filtra per username...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);


        Button updateButton = new Button("Aggiorna", event -> refreshGrid());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, updateButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}
