/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hossien
 */
@Entity
@Table(name = "discount_gallery_for_user")
@NamedQueries({
    @NamedQuery(name = "DiscountGalleryForUser.findAll", query = "SELECT d FROM DiscountGalleryForUser d"),
    @NamedQuery(name = "DiscountGalleryForUser.findById", query = "SELECT d FROM DiscountGalleryForUser d WHERE d.id = :id"),
    @NamedQuery(name = "DiscountGalleryForUser.findByUser", query = "SELECT d FROM DiscountGalleryForUser d WHERE d.userId = :user"),
    @NamedQuery(name = "DiscountGalleryForUser.findByGallery", query = "SELECT d FROM DiscountGalleryForUser d WHERE d.galleryId = :gallery"),
    @NamedQuery(name = "DiscountGalleryForUser.findByUser&Gallery", query = "SELECT d FROM DiscountGalleryForUser d WHERE d.galleryId = :gallery AND d.userId = :user"),
    @NamedQuery(name = "DiscountGalleryForUser.findByUser&Gallery&Discount", query = "SELECT d FROM DiscountGalleryForUser d WHERE d.galleryId = :gallery AND d.userId = :user AND d.discountId = :discount"),
    @NamedQuery(name = "DiscountGalleryForUser.findByNumberUser", query = "SELECT d FROM DiscountGalleryForUser d WHERE d.numberUser = :numberUser"),
    @NamedQuery(name = "DiscountGalleryForUser.findByCreateAt", query = "SELECT d FROM DiscountGalleryForUser d WHERE d.createAt = :createAt"),
    @NamedQuery(name = "DiscountGalleryForUser.findByUpdateAt", query = "SELECT d FROM DiscountGalleryForUser d WHERE d.updateAt = :updateAt")})
public class DiscountGalleryForUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "number_user")
    private int numberUser;
    @Basic(optional = false)
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Basic(optional = false)
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Discount discountId;
    @JoinColumn(name = "gallery_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Gallery galleryId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;

    public DiscountGalleryForUser() {
    }

    public DiscountGalleryForUser(Integer id) {
        this.id = id;
    }

    public DiscountGalleryForUser(Integer id, int numberUser, Date createAt, Date updateAt) {
        this.id = id;
        this.numberUser = numberUser;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberUser() {
        return numberUser;
    }

    public void setNumberUser(int numberUser) {
        this.numberUser = numberUser;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Discount getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Discount discountId) {
        this.discountId = discountId;
    }

    public Gallery getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Gallery galleryId) {
        this.galleryId = galleryId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof DiscountGalleryForUser)) {
            return false;
        }
        DiscountGalleryForUser other = (DiscountGalleryForUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.DiscountGalleryForUser[ id=" + id + " ]";
    }

}
