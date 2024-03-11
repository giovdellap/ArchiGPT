package it.YourOnlineStore.Database;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	// CHILD SERVICES

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(ProductServiceImpl.class);

	@Override
	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}
	
	@Override
	public boolean exists(Integer id) {
		return productRepository.existsById(id);
	}

	@Override
	public void buy(Integer id, Integer amount) {
		Optional<Product> optProduct = productRepository.findById(id);
	    if(optProduct.isPresent()) {
	    	Product product = optProduct.get();
	    	product.setDisponibility(product.getDisponibility()-amount);
	    	productRepository.save(product);
	    }		
	}

}
