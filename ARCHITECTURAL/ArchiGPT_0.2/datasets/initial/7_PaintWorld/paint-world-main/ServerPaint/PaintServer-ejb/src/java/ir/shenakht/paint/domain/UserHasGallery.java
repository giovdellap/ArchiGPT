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
@Table(name = "user_has_gallery")
@NamedQueries({
    @NamedQuery(name = "UserHasGallery.findAll", query = "SELECT u FROM UserHasGallery u"),
    @NamedQuery(name = "UserHasGallery.findById", query = "SELECT u FROM UserHasGallery u WHERE u.id = :id"),
    @NamedQuery(name = "UserHasGallery.findByType", query = "SELECT u FROM UserHasGallery u WHERE u.type = :type"),
    @NamedQuery(name = "UserHasGallery.findByCreateAt", query = "SELECT u FROM UserHasGallery u WHERE u.createAt = :createAt"),
    @NamedQuery(name = "UserHasGallery.findByUpdateAt", query = "SELECT u FROM UserHasGallery u WHERE u.updateAt = :updateAt")})
public class UserHasGallery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    private Integer type;
    @Basic(optional = false)
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Basic(optional = false)
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @JoinColumn(name = "gallery_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Gallery galleryId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;

    public UserHasGallery() {
    }

    public UserHasGallery(Integer id) {
        this.id = id;
    }

    public UserHasGallery(Integer id, Date createAt, Date updateAt) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        if (!(object instanceof UserHasGallery)) {
            return false;
        }
        UserHasGallery other = (UserHasGallery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.UserHasGallery[ id=" + id + " ]";
    }
    
}
