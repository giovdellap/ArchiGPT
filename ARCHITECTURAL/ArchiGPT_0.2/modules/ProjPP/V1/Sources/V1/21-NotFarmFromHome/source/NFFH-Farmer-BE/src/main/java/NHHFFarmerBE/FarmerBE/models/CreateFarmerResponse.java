package NHHFFarmerBE.FarmerBE.models;

public class CreateFarmerResponse {

    private String id;
    private String username;
    private boolean success;


    public CreateFarmerResponse(String id, String username) {
        this.id = id;
        this.username = username;
        this.success = true;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public boolean isSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }


    
    

    
}
