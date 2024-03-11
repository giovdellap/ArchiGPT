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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "gallery_cost")
@NamedQueries({
    @NamedQuery(name = "GalleryCost.findAll", query = "SELECT g FROM GalleryCost g"),
    @NamedQuery(name = "GalleryCost.findById", query = "SELECT g FROM GalleryCost g WHERE g.id = :id"),
    @NamedQuery(name = "GalleryCost.findByTitle", query = "SELECT g FROM GalleryCost g WHERE g.title = :title"),
    @NamedQuery(name = "GalleryCost.findByCreateAt", query = "SELECT g FROM GalleryCost g WHERE g.createAt = :createAt"),
    @NamedQuery(name = "GalleryCost.findByUpdateAt", query = "SELECT g FROM GalleryCost g WHERE g.updateAt = :updateAt"),
    @NamedQuery(name = "GalleryCost.findByType", query = "SELECT g FROM GalleryCost g WHERE g.type = :type"),
    @NamedQuery(name = "GalleryCost.findByPrice", query = "SELECT g FROM GalleryCost g WHERE g.price = :price"),
    @NamedQuery(name = "GalleryCost.findByTermOfValidity", query = "SELECT g FROM GalleryCost g WHERE g.termOfValidity = :termOfValidity")})
public class GalleryCost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 250)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "extra_field")
    private String extraField;
    @Basic(optional = false)
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Basic(optional = false)
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Column(name = "type")
    private Integer type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private float price;
    @Column(name = "term_of_validity")
    private Integer termOfValidity;
    @ManyToMany(mappedBy = "galleryCostList", fetch = FetchType.LAZY)
    private List<Gallery> galleryList;

    public GalleryCost() {
    }

    public GalleryCost(Integer id) {
        this.id = id;
    }

    public GalleryCost(Integer id, Date createAt, Date updateAt, float price) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.price = price;
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

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getTermOfValidity() {
        return termOfValidity;
    }

    public void setTermOfValidity(Integer termOfValidity) {
        this.termOfValidity = termOfValidity;
    }

    public List<Gallery> getGalleryList() {
        return galleryList;
    }

    public void setGalleryList(List<Gallery> galleryList) {
        this.galleryList = galleryList;
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
        if (!(object instanceof GalleryCost)) {
            return false;
        }
        GalleryCost other = (GalleryCost) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.GalleryCost[ id=" + id + " ]";
    }

}
