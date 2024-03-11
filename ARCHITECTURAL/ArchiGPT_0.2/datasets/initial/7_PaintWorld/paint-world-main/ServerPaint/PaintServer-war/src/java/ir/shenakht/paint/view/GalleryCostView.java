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
import ir.shenakht.paint.domain.Gallery;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface GalleryCostView {

    public Integer getId();

    public String getTitle();

    @JsonProperty("extra_field")
    public String getExtraField();

    @JsonProperty("create_at")
    public Date getCreateAt();

    @JsonProperty("update_at")
    public Date getUpdateAt();

    public Integer getType();

    public float getPrice();

    @JsonProperty("term_of_validity")
    public Integer getTermOfValidity();

    @JsonIgnore
    public List<Gallery> getGalleryList();

}
