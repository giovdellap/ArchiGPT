/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author hossien
 */
@Entity
@Table(name = "gallery")
@NamedQueries({
    @NamedQuery(name = "Gallery.findAll", query = "SELECT g FROM Gallery g"),
    @NamedQuery(name = "Gallery.findById", query = "SELECT g FROM Gallery g WHERE g.id = :id"),
    @NamedQuery(name = "Gallery.findByTitle", query = "SELECT g FROM Gallery g WHERE g.title = :title"),
    @NamedQuery(name = "Gallery.findByCreateAt", query = "SELECT g FROM Gallery g WHERE g.createAt = :createAt"),
    @NamedQuery(name = "Gallery.findByUdpateAt", query = "SELECT g FROM Gallery g WHERE g.udpateAt = :udpateAt"),
    @NamedQuery(name = "Gallery.findByType", query = "SELECT g FROM Gallery g WHERE g.type = :type")})
public class Gallery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 500)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Basic(optional = false)
    @Column(name = "udpate_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date udpateAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "type")
    private int type;
    @Lob
    @Size(max = 65535)
    @Column(name = "extra_field")
    private String extraField;
    @JoinTable(name = "gallery_has_gallery_cost", joinColumns = {
        @JoinColumn(name = "gallery_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "gallery_cost_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<GalleryCost> galleryCostList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "galleryId", fetch = FetchType.LAZY)
    private List<Picture> pictureList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "galleryId", fetch = FetchType.LAZY)
    private List<UserHasGallery> userHasGalleryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "galleryId", fetch = FetchType.LAZY)
    private List<DiscountGalleryForUser> discountGalleryForUserList;

    public Gallery() {
    }

    public Gallery(Integer id) {
        this.id = id;
    }

    public Gallery(Integer id, Date createAt, Date udpateAt, int type) {
        this.id = id;
        this.createAt = createAt;
        this.udpateAt = udpateAt;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUdpateAt() {
        return udpateAt;
    }

    public void setUdpateAt(Date udpateAt) {
        this.udpateAt = udpateAt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    public List<GalleryCost> getGalleryCostList() {
        return galleryCostList;
    }

    public void setGalleryCostList(List<GalleryCost> galleryCostList) {
        this.galleryCostList = galleryCostList;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    public List<UserHasGallery> getUserHasGalleryList() {
        return userHasGalleryList;
    }

    public void setUserHasGalleryList(List<UserHasGallery> userHasGalleryList) {
        this.userHasGalleryList = userHasGalleryList;
    }

    public List<DiscountGalleryForUser> getDiscountGalleryForUserList() {
        return discountGalleryForUserList;
    }

    public void setDiscountGalleryForUserList(List<DiscountGalleryForUser> discountGalleryForUserList) {
        this.discountGalleryForUserList = discountGalleryForUserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gallery)) {
            return false;
        }
        Gallery other = (Gallery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.Gallery[ id=" + id + " ]";
    }

}
