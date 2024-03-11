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

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface PictureView {

    public Integer getId();

    public String getTitle();

    @JsonProperty("url_thumbnail")
    public String getUrlThumbnail();

    @JsonProperty("url_main")
    public String getUrlMain();

    @JsonProperty("create_at")
    public Date getCreateAt();

    @JsonProperty("update_at")
    public Date getUpdateAt();

    @JsonIgnore
    public Gallery getGalleryId();

}
