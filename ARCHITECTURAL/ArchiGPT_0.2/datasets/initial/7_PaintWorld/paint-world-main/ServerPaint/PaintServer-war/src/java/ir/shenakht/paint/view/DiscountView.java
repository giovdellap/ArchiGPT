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
import java.util.Date;
import java.util.List;

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface DiscountView {

    public Integer getId();

    public String getTitle();

    public String getDescription();

    @JsonProperty("create_at")
    public Date getCreateAt();

    @JsonProperty("update_at")
    public Date getUpdateAt();

    public int getDiscount();

    @JsonProperty("start_discount")
    public Date getStartDiscount();

    @JsonProperty("end_discount")
    public Date getEndDiscount();

    @JsonProperty("extra_field")
    public String getExtraField();

    public Integer getType();

    public Integer getRepeat();

    @JsonIgnore
    public List<DiscountGalleryForUser> getDiscountGalleryForUserList();

}
