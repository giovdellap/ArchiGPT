package NHHFFarmerBE.FarmerBE.requests;

import NHHFFarmerBE.FarmerBE.entities.Farmer;

public record CreateFarmerInput(String username, String email, String password, String image, String area, String address) {
    public Farmer toFarmer() {
        Farmer farmer = new Farmer();

        farmer.setUsername(username);
        farmer.setEmail(email);
        farmer.setPassword(password);
        farmer.setImage(image);
        farmer.setArea(area);
        farmer.setAddress(address);

        return farmer;
    }
}