package NHHFFarmerBE.FarmerBE.models;

public class ModifyProductResponse {

    private String id;
    private String title;
    private boolean success;

    public ModifyProductResponse(String id, String title){
        this.id = id;
        this.title = title;
        this.success = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    
    
}
