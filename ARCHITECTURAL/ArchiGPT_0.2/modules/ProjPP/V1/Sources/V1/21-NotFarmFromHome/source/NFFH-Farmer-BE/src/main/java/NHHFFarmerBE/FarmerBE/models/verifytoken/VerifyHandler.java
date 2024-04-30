package NHHFFarmerBE.FarmerBE.models.verifytoken;


import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import NHHFFarmerBE.FarmerBE.entities.Farmer;
import NHHFFarmerBE.FarmerBE.services.FarmerService;

public class VerifyHandler {

    private String authAddress = "http://auth:9701/verifyToken";        
    public final FarmerService farmerService;
    private String role = "";
    private Farmer farmer;
    private boolean success;
    
    public VerifyHandler(FarmerService farmerService) {
        this.farmerService = farmerService;
    }
    
    public void verify(String token) {
        
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<VerifyTokenRequest> req = new HttpEntity<VerifyTokenRequest>(
            new VerifyTokenRequest(token)
        );
        ResponseEntity<VerifyTokenResponse> res = restTemplate.postForEntity(authAddress, req, VerifyTokenResponse.class);
        this.success = res.getBody().isSuccess();
        if(this.success) {
            this.role = res.getBody().getRole();
            if (!this.getRole().equals("admin")) {
                this.farmer = this.farmerService.findByEmail(res.getBody().getEmail());
            } 
        }
    }
    
    public Farmer getFarmer() {
            return farmer;
        }
        
    public String getRole() {
        return role;
    }
    
    public boolean isSuccess() {
        return success;
    }    
}
