package Order.Order.entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;

@Table(name = "OrderPart")
@Entity
public class OrderPart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idOrderPart", nullable = false)
    private Integer id;

    @Column(name = "idOrder", length = 200, nullable = false)
    private int idOrder;
    
    @Column(name = "seller", length = 200, nullable = false)
    private int seller;

    @Column(name = "productsList", nullable = false)
    private String productList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getSeller() {
        return seller;
    }

    public void setSeller(int seller) {
        this.seller = seller;
    }

    public String getProductList() {
        return productList;
    }

    public void setProductList(String productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "OrderPart [id=" + id + ", idOrder=" + idOrder + ", seller=" + seller + ", productList=" + productList
                + "]";
    }

}
