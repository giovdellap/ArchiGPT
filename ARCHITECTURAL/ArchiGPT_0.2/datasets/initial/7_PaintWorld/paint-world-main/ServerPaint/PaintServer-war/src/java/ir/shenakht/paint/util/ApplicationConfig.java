/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.util;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author hossien
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ir.shenakht.paint.services.CityResource.class);
        resources.add(ir.shenakht.paint.services.DiscountResource.class);
        resources.add(ir.shenakht.paint.services.GalleryCostResource.class);
        resources.add(ir.shenakht.paint.services.GalleryResource.class);
        resources.add(ir.shenakht.paint.services.JudgeUserResource.class);
        resources.add(ir.shenakht.paint.services.JudgmentResource.class);
        resources.add(ir.shenakht.paint.services.LoginResource.class);
        resources.add(ir.shenakht.paint.services.ParticipantsResource.class);
        resources.add(ir.shenakht.paint.services.PaymentIrancelResource.class);
        resources.add(ir.shenakht.paint.services.PictureResource.class);
        resources.add(ir.shenakht.paint.services.ProductsResource.class);
        resources.add(ir.shenakht.paint.services.ProviceResource.class);
        resources.add(ir.shenakht.paint.services.QuestionResource.class);
        resources.add(ir.shenakht.paint.services.RacingResource.class);
        resources.add(ir.shenakht.paint.services.ScaleQuestionForRacingResource.class);
        resources.add(ir.shenakht.paint.services.UserResource.class);
    }
    
}
