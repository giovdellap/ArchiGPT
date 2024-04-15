package NHHFFarmerBE.FarmerBE.requests;

public record LoginInput(String email) {
    public String ToStringEmail(){
        return email;
    }
}