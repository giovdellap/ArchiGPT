package NHHFFarmerBE.FarmerBE.models.verifytoken;

public class VerifyTokenRequest {
    private String token;
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public VerifyTokenRequest(String t) {
        token = t;
    }

}
