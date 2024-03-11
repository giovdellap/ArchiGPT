package com.example.application.views.main;

//import com.example.application.data.service.FineRepository;
//import com.example.application.data.service.UserRepository;
import com.example.application.views.Paths;
import com.example.application.views.login.LoginView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;
@Route(Paths.rootRoute)


@PageTitle("Pubblica amministrazione Repubblica Italiana")
public class MainView extends AppLayout {

    private final Tabs menu;

    public MainView() {
        menu = createMenu();
        setContent(createHeaderContent());
    }

    private Component createHeaderContent() {
        final VerticalLayout layout = new VerticalLayout();
        layout.getThemeList().add("light");
        layout.setPadding(true);
        layout.setSpacing(false);
        layout.setId("home");
        layout.setWidthFull();
        layout.setHeightFull();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);

        final Span brand = new Span();
        final Anchor brandLink = new Anchor("/", brand);
        brandLink.addClassName("navbar-brand");
        layout.add(new Image("images/repita.jpg", "RepIta"));
        layout.add(new H1("Pubblica amministrazione Repubblica italiana"));
        layout.add(new H2("Piattaforma di gestione delle multe"));
        layout.add(brandLink);

        layout.add(menu);

        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        return new Tab[] {
                new Tab(createRouterLink("login", VaadinIcon.HOME, LoginView.class))
        };
    }

    private RouterLink createRouterLink(String translationKey, VaadinIcon viewIcon,
                                        Class<? extends Component> navigationTarget) {
        final RouterLink routerLink =
                new RouterLink(getTranslation(translationKey), navigationTarget);
        routerLink.addComponentAsFirst(viewIcon.create());
        routerLink.addClassNames("flex", "gap-s", "uppercase");
        routerLink.setText("LOGIN");
        return routerLink;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        updateChrome();
    }

    private void updateChrome() {
        getTabWithCurrentRoute().ifPresent(menu::setSelectedTab);
    }

    private Optional<Tab> getTabWithCurrentRoute() {
        final String currentRoute = RouteConfiguration.forSessionScope()
                .getUrl(getContent().getClass());
        return menu.getChildren().filter(tab -> hasLink(tab, currentRoute))
                .findFirst().map(Tab.class::cast);
    }

    private boolean hasLink(Component tab, String currentRoute) {
        return tab.getChildren().filter(RouterLink.class::isInstance)
                .map(RouterLink.class::cast).map(RouterLink::getHref)
                .anyMatch(currentRoute::equals);
    }

}