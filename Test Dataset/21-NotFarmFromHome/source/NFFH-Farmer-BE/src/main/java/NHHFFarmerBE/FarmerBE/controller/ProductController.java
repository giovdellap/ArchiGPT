package NHHFFarmerBE.FarmerBE.controller;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import NHHFFarmerBE.FarmerBE.entities.Farmer;
import NHHFFarmerBE.FarmerBE.entities.Product;
import NHHFFarmerBE.FarmerBE.models.AreaPageProductResponse;
import NHHFFarmerBE.FarmerBE.models.CreateProductResponse;
import NHHFFarmerBE.FarmerBE.models.ModifyProductResponse;
import NHHFFarmerBE.FarmerBE.models.SellerPageProductResponse;
import NHHFFarmerBE.FarmerBE.models.verifytoken.VerifyHandler;
import NHHFFarmerBE.FarmerBE.models.verifytoken.VerifyTokenRequest;
import NHHFFarmerBE.FarmerBE.models.verifytoken.VerifyTokenResponse;
import NHHFFarmerBE.FarmerBE.requests.CreateProductInput;
import NHHFFarmerBE.FarmerBE.services.FarmerService;
import NHHFFarmerBE.FarmerBE.services.ProductService;
import NHHFFarmerBE.FarmerBE.repositories.FarmerRepository;
import NHHFFarmerBE.FarmerBE.repositories.ProductRepository;

@RestController
public class ProductController {
    
    @Autowired
    public final ProductService productService;
    public final ProductRepository productrepo;
    public final FarmerService farmerService;
    public final FarmerRepository farmerRepository;


    public ProductController(
        ProductService productService, 
        ProductRepository productrepo,
        FarmerService farmerService,
        FarmerRepository farmerRepository
    ){
        this.productService = productService;
        this.productrepo = productrepo;
        this.farmerService = farmerService;
        this.farmerRepository = farmerRepository;
    }


    //Add a product

    @PostMapping("/product/add")
    public ResponseEntity<CreateProductResponse> createTask(
        @RequestHeader("token") String token,
        @RequestBody CreateProductInput createProductInput
        ) {
        boolean error = false;
        VerifyHandler handler = new VerifyHandler(this.farmerService);
        handler.verify(token);

        if(!handler.isSuccess())
            error = true;
        if(!createProductInput.toProduct().getSeller().equals(handler.getFarmer().getUsername())) {
            error = true;
        }

        if(!error || handler.getRole().equals("admin")) {
            return addProduct(createProductInput);
        } else {
            CreateProductResponse res = new CreateProductResponse("", "");
            res.setSuccess(false);
            return new ResponseEntity<CreateProductResponse>(res, HttpStatus.OK);
        }
    }

    private ResponseEntity<CreateProductResponse> addProduct(CreateProductInput createProductInput) {
        Product createdProduct = productService.create(createProductInput.toProduct());    
        CreateProductResponse response = new CreateProductResponse(
            String.valueOf(createdProduct.getId()), 
            createdProduct.getTitle()
        );
        return new ResponseEntity<CreateProductResponse>(response, HttpStatus.CREATED);
    }

    //Return all products

    @GetMapping("/allproducts")
    public ResponseEntity<List<Product>> allTasks() {
        List<Product> ProductList = productService.findAll();
        return new ResponseEntity<>(ProductList, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> producttemp = productService.findById(id);

        if (producttemp.isPresent()){
            Product product = producttemp.get();
            return new ResponseEntity<Product>(product, HttpStatus.OK);

        }

        Product product = null;
        return new ResponseEntity<Product>(product, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //Delete a product using ID

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ModifyProductResponse> deleteTask(
        @RequestHeader("token") String token,
        @PathVariable int id
        ) {

        boolean error = false;
        VerifyHandler handler = new VerifyHandler(this.farmerService);
        handler.verify(token);

        if(!handler.isSuccess())
            error = true;
        if((!error) || handler.getRole().equals("admin")) {
            productService.delete(id);
            return new ResponseEntity<ModifyProductResponse>(
                new ModifyProductResponse("", ""), HttpStatus.OK);
        } else {
            ModifyProductResponse res = new ModifyProductResponse("", "");
            res.setSuccess(false);
            return new ResponseEntity<ModifyProductResponse>(res, HttpStatus.OK);
        }
    }


    @GetMapping("/product/findbyseller")
    public ResponseEntity<SellerPageProductResponse> getProductBySellerPage(@RequestParam String seller, int page){
        
        int pageSize = 12;
        List<Product> productList = productService.getProductBySeller(seller);
        if (productList.isEmpty()){
            return new ResponseEntity<SellerPageProductResponse>(null, null, HttpStatus.BAD_REQUEST);
        }

        List<Product> SubList;
        int totalPageNumber = (int)Math.round((productList.size()/pageSize)+0.5);
        if (page > totalPageNumber){
            return new ResponseEntity<SellerPageProductResponse>(null, null, HttpStatus.NOT_FOUND);

        }
        if ((page == totalPageNumber) && (productList.size() % pageSize != 0)){
            SubList = productList.subList((page-1) * pageSize, page * pageSize - (page * pageSize - (productList.size() % pageSize)));
        } else{
            SubList = productList.subList((page-1) * pageSize, page * pageSize);
        }


        SellerPageProductResponse response = new SellerPageProductResponse(SubList, page, totalPageNumber);

        return new ResponseEntity<SellerPageProductResponse>(response, HttpStatus.OK);
    }


    @PostMapping("/product/modify/{id}")
    public ResponseEntity<ModifyProductResponse> modifyProductById(
        @RequestHeader("token") String token,
        @PathVariable String id, 
        @RequestBody CreateProductInput createProductInput){
        
        boolean error = false;
        VerifyHandler handler = new VerifyHandler(this.farmerService);
        handler.verify(token);

        if(!handler.isSuccess())
            error = true;
        if(!createProductInput.toProduct().getSeller().equals(handler.getFarmer().getUsername())) {
            error = true;
        }

        if(!error || handler.getRole().equals("admin")) {
            return modifyProductById(createProductInput, id);
        } else {
            ModifyProductResponse res = new ModifyProductResponse("", "");
            res.setSuccess(false);
            return new ResponseEntity<ModifyProductResponse>(res, HttpStatus.OK);
        }
    }
    
    public ResponseEntity<ModifyProductResponse> modifyProductById(
        CreateProductInput createProductInput,
        String id
    ) {

        Product createdProduct = createProductInput.toProduct();
        int idString = Integer.valueOf(id);
        Optional<Product> optionalProduct = productService.findById(idString);

        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setTitle(createdProduct.getTitle());
            product.setSeller(createdProduct.getSeller());
            product.setImage(createdProduct.getImage());
            product.setDescription(createdProduct.getDescription());
            product.setPrice(createdProduct.getPrice());
            product.setWeight(createdProduct.getWeight());
            product.setAvailability(createdProduct.getAvailability());
            product.setId(idString);


            ModifyProductResponse response = new ModifyProductResponse(id, product.getTitle());
            Product dbres = productService.create(product);
            response.setSuccess(dbres != null);
            return new ResponseEntity<ModifyProductResponse>(response, HttpStatus.OK);
        } else{
            ModifyProductResponse response = new ModifyProductResponse("", "");
            response.setSuccess(false);
            return new ResponseEntity<ModifyProductResponse>(response, HttpStatus.NOT_MODIFIED);
        }
    }



}
