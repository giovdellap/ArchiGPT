/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.view;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ir.shenakht.paint.domain.Discount;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.domain.User;
import java.util.Date;

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface DiscountGalleryForUserView {

    public Integer getId();

    @JsonProperty("number_user")
    public int getNumberUser();

    @JsonProperty("create_at")
    public Date getCreateAt();

    @JsonProperty("update_at")
    public Date getUpdateAt();

    @JsonProperty("discount")
    public Discount getDiscountId();

    @JsonProperty("gallery")
    public Gallery getGalleryId();

    @JsonProperty("user")
    public User getUserId();

}
