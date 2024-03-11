//package com.example.application.data.service;
//
//
//import com.example.application.data.entity.Fine;
//import com.example.application.data.entity.Role;
//import com.example.application.data.entity.User;
//import com.example.application.views.fines.GestiteView;
//import com.example.application.views.main.MainView;
//import com.vaadin.flow.router.RouterLink;
//
//import java.util.HashSet;
//
//public class FineRepository {
//
//    private static HashSet<Fine> fines=new HashSet<>();
//
//    public FineRepository(){
//    }
//    public static void InitializeRepository(){
//        //fines =  new HashSet<>();
//        fines.add(new Fine("1","1",new RouterLink("pdf", MainView.class),3,128,"Luigi Antonelli","1839505"));
//        fines.add(new Fine("2","2",new RouterLink("pdf", MainView.class),4,129,"Luca Gaudenzi","1851425"));
//    }
//    public static HashSet<Fine> getFines(){
//        return fines;
//    }
//    public static Fine getByCode(String code) {
//        for(Fine f : fines){
//            if(f.Codice_Multa.equals(code)){
//                return f;
//            }
//        }
//        return null;
//    }
//}