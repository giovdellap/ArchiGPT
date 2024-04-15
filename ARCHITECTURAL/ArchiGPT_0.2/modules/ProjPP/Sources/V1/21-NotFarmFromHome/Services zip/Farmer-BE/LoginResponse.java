package NHHFFarmerBE.FarmerBE.models;

public class LoginResponse {
    
    private String password;
    private boolean success;
    private String id;
    private String username;
    
    public LoginResponse(String password, String id, String username, boolean success){
        this.password = password;
        this.success = success;
        this.id = id;
        this.username = username;
    }
    
        public String getUsername() {
            return username;
        }
    
        public void setUsername(String username) {
            this.username = username;
        }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    

}
