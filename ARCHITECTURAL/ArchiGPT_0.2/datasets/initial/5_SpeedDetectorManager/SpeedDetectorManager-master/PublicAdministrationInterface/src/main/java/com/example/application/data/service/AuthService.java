package com.example.application.data.service;

import com.example.application.views.Paths;
import com.example.application.views.admin.AdminView;
import com.example.application.views.admin.RegistrationView;
import com.example.application.views.admin.UserInfoView;

import com.example.application.views.admin.UsersView;
import com.example.application.views.fines.FinesView;
import com.example.application.views.fines.GestiteView;
import com.example.application.views.fines.InfoFineView;
import com.example.application.views.fines.NuoveView;
import com.example.application.views.home.HomeView;
import com.example.application.views.logout.LogoutView;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import it.uniroma1.commons.entity.User;
import it.uniroma1.commons.enums.Role;
import it.uniroma1.commons.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;



    private HashSet<String> UserLayoutRoutes=new HashSet<>();
    private HashSet<String> AdminLayoutRoutes=new HashSet<>();
    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {

    }

    public class AuthException extends Exception {

    }



    public void authenticate(String username, String password, String region) throws AuthException {
        AdminLayoutRoutes.add(Paths.registrationRoute);
        AdminLayoutRoutes.add(Paths.adminUsersRoute);
        AdminLayoutRoutes.add(Paths.adminUsersFineRoute);
        AdminLayoutRoutes.add(Paths.adminUsersInfoRoute);
        AdminLayoutRoutes.add(Paths.adminInfoRoute);
        //AdminLayoutRoutes.add(adminInfoUsersRoute);

        //AdminLayoutRoutes.add(userInfoFinesRoute);


        UserLayoutRoutes.add(Paths.infoFineRoute);
        UserLayoutRoutes.add(Paths.newFinesRoute);
        UserLayoutRoutes.add(Paths.managedFinesRoute);
        UserLayoutRoutes.add(Paths.userInfoRoute);
        UserLayoutRoutes.add(Paths.userInfoFinesRoute);



        //User user = userRepository.getByUsername(username);
        //prendi utente dal database
        Optional<User> optionalUser = userRepository.findById(username);
        User user = optionalUser.isPresent()?optionalUser.get():null;
        if (user != null && user.checkPassword(password) && user.checkRegion(region)/*user.isActive()*/) {

            VaadinSession.getCurrent().setAttribute(User.class, user);  // FORSE DA TOGLIERE
            createRoutes(user.getRole());
            if(user.getRole().equals(Role.ADMIN)){
                UI.getCurrent().navigate(Paths.adminUsersRoute);
            }
            else{
                UI.getCurrent().navigate(Paths.newFinesRoute);
            }
        } else {
            throw new AuthException();
        }
    }


    private void createRoutes(Role role) {
        getAuthorizedRoutes(role).stream()
                .forEach(route ->{
                            if(AdminLayoutRoutes.contains(route.route)){
                                RouteConfiguration.forSessionScope().setRoute(
                                        route.route, route.view, AdminView.class);}

                            else if(UserLayoutRoutes.contains(route.route)){
                                RouteConfiguration.forSessionScope().setRoute(
                                        route.route, route.view, FinesView.class);}
                            else{
                                RouteConfiguration.forSessionScope().setRoute(
                                        route.route, route.view, MainView.class);
                            }
                        }
                );

    }

    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
        ArrayList<AuthorizedRoute> routes = new ArrayList<>();
        routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
        if (role.equals(Role.USER)) {
            //routes.add(new AuthorizedRoute("login", "Login", LoginView.class));
            //routes.add(new AuthorizedRoute("multe", "Multe", FinesView.class));
            routes.add(new AuthorizedRoute(Paths.newFinesRoute, "Multe Nuove", NuoveView.class));
            routes.add(new AuthorizedRoute(Paths.managedFinesRoute, "Multe Gestite", GestiteView.class));
            routes.add(new AuthorizedRoute(Paths.infoFineRoute, "Informazioni Multa", InfoFineView.class));
            routes.add(new AuthorizedRoute(Paths.userInfoRoute, "Informazioni Utente", HomeView.class));
            //routes.add(new AuthorizedRoute(Paths.userInfoFinesRoute, "Informazioni Multe", UserInfoView.class));




        }
        else if (role.equals(Role.ADMIN)) {

            routes.add(new AuthorizedRoute(Paths.registrationRoute, "Registrazione", RegistrationView.class));
            routes.add(new AuthorizedRoute(Paths.adminUsersRoute, "Utenti", UsersView.class));
            routes.add(new AuthorizedRoute(Paths.adminUsersInfoRoute,"Informazioni Utente", UserInfoView.class));
            routes.add(new AuthorizedRoute(Paths.adminUsersFineRoute,"Informazioni Multa", InfoFineView.class));
            routes.add(new AuthorizedRoute(Paths.adminInfoRoute, "Informazioni Utente", HomeView.class));


        }
        return routes;
    }

   public void register(String username, String name, String surname, String password, String region, Role role) {

       User newUser = new User();
       boolean useLetters = true;
       boolean useNumbers = false;
       int length = 10;
       String passwordSalt = RandomStringUtils.random(length, useLetters, useNumbers);

       newUser.setUsername(username);
       newUser.setName(name);
       newUser.setSurname(surname);
       newUser.setPasswordSalt(passwordSalt);
       newUser.setPasswordHash(DigestUtils.sha1Hex(password + passwordSalt));
       newUser.setRegion(region);


       newUser.setRole(Role.USER);
//       newUser.setActive(false); // DA MODIFICARE
//       newUser.setActivationCode(username); //DA MODIFICARE

       User creator = VaadinSession.getCurrent().getAttribute(User.class);
       newUser.setCreator(creator);

       //salvataggio dell'utente nel database
       userRepository.save(newUser);
   }

    /*public void activate(String activationCode) throws AuthException {
        //User user = userRepository.getByActivationCode(activationCode)
        if (user != null) {
            user.setActive(true);
            userRepository.save(user);
        } else {
            throw new AuthException();
        }
    }*/

}
