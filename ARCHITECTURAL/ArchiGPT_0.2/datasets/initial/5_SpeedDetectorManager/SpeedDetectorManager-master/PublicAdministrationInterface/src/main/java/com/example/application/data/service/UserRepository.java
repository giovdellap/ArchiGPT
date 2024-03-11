//package com.example.application.data.service;
//
//
//import com.example.application.data.entity.Role;
//import com.example.application.data.entity.User;
//
//import java.util.HashSet;
//
//public class UserRepository {
//
//    private static HashSet<User> users = new HashSet<>();
//    //chiamata al costruttore fatta in MainView
//    public UserRepository(){
//        //users =  new HashSet<>();
//        users.add(new User("admin", "a", Role.ADMIN, "Lazio"));
//
//        users.add(new User("michael", "secret", Role.USER, "Lombardia"));
//        users.add(new User("dwight", "secret", Role.USER, "Lazio"));
//        users.add(new User("jim", "secret", Role.USER, "Campania"));
//        users.add(new User("pam", "secret", Role.USER, "Abruzzo"));
//        users.add(new User("binotto", "123", Role.USER, "Molise"));
//    }
//
//    public static void InitializeRepository(){
//        users =  new HashSet<>();
//        users.add(new User("admin", "a", Role.ADMIN, "Lazio"));
//
//        users.add(new User("michael", "secret", Role.USER, "Lombardia"));
//        users.add(new User("dwight", "secret", Role.USER, "Lazio"));
//        users.add(new User("jim", "secret", Role.USER, "Campania"));
//        users.add(new User("pam", "secret", Role.USER, "Abruzzo"));
//        users.add(new User("binotto", "123", Role.USER, "Molise"));
//    }
//    public static User getByUsername(String username) {
//        for(User u : users){
//            if(u.getUsername().equals(username)){
//                return u;
//            }
//        }
//        return null;
//    }
//}