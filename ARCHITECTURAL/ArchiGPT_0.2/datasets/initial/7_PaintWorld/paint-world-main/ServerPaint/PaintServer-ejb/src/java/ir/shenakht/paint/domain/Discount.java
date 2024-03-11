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
import javax.persistence.Lob;
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
@Table(name = "discount")
@NamedQueries({
    @NamedQuery(name = "Discount.findAll", query = "SELECT d FROM Discount d"),
    @NamedQuery(name = "Discount.findById", query = "SELECT d FROM Discount d WHERE d.id = :id"),
    @NamedQuery(name = "Discount.findByTitle", query = "SELECT d FROM Discount d WHERE d.title = :title"),
    @NamedQuery(name = "Discount.findByCreateAt", query = "SELECT d FROM Discount d WHERE d.createAt = :createAt"),
    @NamedQuery(name = "Discount.findByUpdateAt", query = "SELECT d FROM Discount d WHERE d.updateAt = :updateAt"),
    @NamedQuery(name = "Discount.findByDiscount", query = "SELECT d FROM Discount d WHERE d.discount = :discount"),
    @NamedQuery(name = "Discount.findByStartDiscount", query = "SELECT d FROM Discount d WHERE d.startDiscount = :startDiscount"),
    @NamedQuery(name = "Discount.findByEndDiscount", query = "SELECT d FROM Discount d WHERE d.endDiscount = :endDiscount"),
    @NamedQuery(name = "Discount.findByType", query = "SELECT d FROM Discount d WHERE d.type = :type"),
    @NamedQuery(name = "Discount.findByRepeat", query = "SELECT d FROM Discount d WHERE d.repeat = :repeat")})
public class Discount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 400)
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
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "discount")
    private int discount;
    @Column(name = "start_discount")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDiscount;
    @Column(name = "end_discount")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDiscount;
    @Lob
    @Size(max = 65535)
    @Column(name = "extra_field")
    private String extraField;
    @Column(name = "type")
    private Integer type;
    @Column(name = "repeat")
    private Integer repeat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discountId", fetch = FetchType.LAZY)
    private List<DiscountGalleryForUser> discountGalleryForUserList;

    public Discount() {
    }

    public Discount(Integer id) {
        this.id = id;
    }

    public Discount(Integer id, Date createAt, Date updateAt, int discount) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.discount = discount;
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

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Date getStartDiscount() {
        return startDiscount;
    }

    public void setStartDiscount(Date startDiscount) {
        this.startDiscount = startDiscount;
    }

    public Date getEndDiscount() {
        return endDiscount;
    }

    public void setEndDiscount(Date endDiscount) {
        this.endDiscount = endDiscount;
    }

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRepeat() {
        return repeat;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
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
        if (!(object instanceof Discount)) {
            return false;
        }
        Discount other = (Discount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.Discount[ id=" + id + " ]";
    }
    
}
