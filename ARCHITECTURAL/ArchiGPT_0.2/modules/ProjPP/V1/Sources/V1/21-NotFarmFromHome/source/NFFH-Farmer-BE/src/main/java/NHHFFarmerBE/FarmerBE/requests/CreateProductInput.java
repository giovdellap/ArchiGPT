package NHHFFarmerBE.FarmerBE.requests;

import NHHFFarmerBE.FarmerBE.entities.Product;

public record CreateProductInput(String title, String seller, String image, String description, String price, String weight, boolean availability) {
    public Product toProduct() {
        Product product = new Product();

        product.setTitle(title);
        product.setSeller(seller);
        product.setImage(image);
        product.setDescription(description);
        product.setPrice(price);
        product.setWeight(weight);

        return product;
    }
}
