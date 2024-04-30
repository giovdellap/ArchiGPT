package NHHFFarmerBE.FarmerBE.models;

import java.util.List;

import NHHFFarmerBE.FarmerBE.entities.Product;

public class SellerPageProductResponse {
    
    private List<Product> products;
    private int page;
    private int total;      //Total page number
    
    
    public SellerPageProductResponse(List<Product> products, int page, int total){
        this.products = products;
        this.page = page;
        this.total = total;
    }

    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

    
}
