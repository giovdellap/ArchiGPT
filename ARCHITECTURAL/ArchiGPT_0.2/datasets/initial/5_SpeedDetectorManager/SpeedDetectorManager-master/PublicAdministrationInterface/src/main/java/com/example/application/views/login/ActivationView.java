package com.example.application.views.login;

import com.example.application.data.service.AuthService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLink;

import java.util.List;
import java.util.Map;

//@Route("activate")
public class ActivationView extends Composite<Component> implements BeforeEnterObserver {

    private VerticalLayout layout;

    private final AuthService authService;

    public ActivationView(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        //try {
        Map<String, List<String>> params = event.getLocation().getQueryParameters().getParameters();
        String code = params.get("code").get(0); //ERROR IN http://localhost:8080/activate : Cannot invoke "java.util.List.get(int)" because the return value of "java.util.Map.get(Object)" is null
        //authService.activate(code);
        layout.add(
                new Text("Account activated."),
                new RouterLink("Login", LoginView.class)
        );

    }

    @Override
    protected Component initContent() {
        layout = new VerticalLayout();
        return layout;
    }
}
