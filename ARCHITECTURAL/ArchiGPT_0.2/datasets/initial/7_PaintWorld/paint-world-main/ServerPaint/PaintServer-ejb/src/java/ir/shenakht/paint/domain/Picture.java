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
import javax.validation.constraints.Size;

/**
 *
 * @author hossien
 */
@Entity
@Table(name = "picture")
@NamedQueries({
    @NamedQuery(name = "Picture.findAll", query = "SELECT p FROM Picture p"),
    @NamedQuery(name = "Picture.findById", query = "SELECT p FROM Picture p WHERE p.id = :id"),
    @NamedQuery(name = "Picture.findByGallery", query = "SELECT p FROM Picture p WHERE p.galleryId = :gallery"),
    @NamedQuery(name = "Picture.findById&Gallery", query = "SELECT p FROM Picture p WHERE p.id = :id AND p.galleryId = :gallery"),
    @NamedQuery(name = "Picture.findByTitle", query = "SELECT p FROM Picture p WHERE p.title = :title"),
    @NamedQuery(name = "Picture.findByUrlThumbnail", query = "SELECT p FROM Picture p WHERE p.urlThumbnail = :urlThumbnail"),
    @NamedQuery(name = "Picture.findByUrlMain", query = "SELECT p FROM Picture p WHERE p.urlMain = :urlMain"),
    @NamedQuery(name = "Picture.findByCreateAt", query = "SELECT p FROM Picture p WHERE p.createAt = :createAt"),
    @NamedQuery(name = "Picture.findByUpdateAt", query = "SELECT p FROM Picture p WHERE p.updateAt = :updateAt")})
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 500)
    @Column(name = "title")
    private String title;
    @Size(max = 400)
    @Column(name = "url_thumbnail")
    private String urlThumbnail;
    @Size(max = 400)
    @Column(name = "url_main")
    private String urlMain;
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

    public Picture() {
    }

    public Picture(Integer id) {
        this.id = id;
    }

    public Picture(Integer id, Date createAt, Date updateAt) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public String getUrlMain() {
        return urlMain;
    }

    public void setUrlMain(String urlMain) {
        this.urlMain = urlMain;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Picture)) {
            return false;
        }
        Picture other = (Picture) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.Picture[ id=" + id + " ]";
    }

}
