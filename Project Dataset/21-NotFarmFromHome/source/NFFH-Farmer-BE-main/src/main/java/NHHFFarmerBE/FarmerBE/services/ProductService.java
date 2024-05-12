package NHHFFarmerBE.FarmerBE.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NHHFFarmerBE.FarmerBE.entities.Farmer;
import NHHFFarmerBE.FarmerBE.entities.Product;
import NHHFFarmerBE.FarmerBE.repositories.ProductRepository;


@Service
public class ProductService implements IProductService{
        
    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product){
        return this.productRepository.save(product);
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
        return ;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        this.productRepository.findAll().forEach(productList::add);
        return productList;
    }

    @Override
    public List<Product> getProductBySeller(String seller){
        return productRepository.findBySeller(seller);
    }

    @Override
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

}
