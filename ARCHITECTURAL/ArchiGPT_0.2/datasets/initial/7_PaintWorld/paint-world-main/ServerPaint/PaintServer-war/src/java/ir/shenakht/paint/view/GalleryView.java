/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.view;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ir.shenakht.paint.domain.DiscountGalleryForUser;
import ir.shenakht.paint.domain.GalleryCost;
import ir.shenakht.paint.domain.Picture;
import ir.shenakht.paint.domain.UserHasGallery;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface GalleryView {

    public Integer getId();

    public String getTitle();

    public String getDescription();

    @JsonProperty("create_at")
    public Date getCreateAt();

    @JsonProperty("update_at")
    public Date getUdpateAt();

    public int getType();

    @JsonProperty("extra_field")
    public String getExtraField();

    @JsonProperty("gallery_cost_list")
    public List<GalleryCost> getGalleryCostList();

    @JsonProperty("picture_list")
    public List<Picture> getPictureList();

    @JsonIgnore
    public List<UserHasGallery> getUserHasGalleryList();

    @JsonIgnore
    public List<DiscountGalleryForUser> getDiscountGalleryForUserList();
}
