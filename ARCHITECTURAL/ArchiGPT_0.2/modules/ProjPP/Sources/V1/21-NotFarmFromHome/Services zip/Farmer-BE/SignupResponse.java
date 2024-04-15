package NHHFFarmerBE.FarmerBE.models;

public class SignupResponse {

    private boolean success = false;
    private String id;
    
    public SignupResponse(String id, boolean success) {
        this.success = success;
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}
