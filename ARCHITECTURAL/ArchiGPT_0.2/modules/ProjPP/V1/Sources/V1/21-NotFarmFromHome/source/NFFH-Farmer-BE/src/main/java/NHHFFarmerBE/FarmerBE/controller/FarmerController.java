package NHHFFarmerBE.FarmerBE.controller;

import NHHFFarmerBE.FarmerBE.entities.Farmer;
import NHHFFarmerBE.FarmerBE.models.AreaPageProductResponse;
import NHHFFarmerBE.FarmerBE.models.GetFarmerLightResponse;
import NHHFFarmerBE.FarmerBE.models.LoginResponse;
import NHHFFarmerBE.FarmerBE.models.SignupResponse;
import NHHFFarmerBE.FarmerBE.models.verifytoken.VerifyHandler;
import NHHFFarmerBE.FarmerBE.requests.CreateFarmerInput;
import NHHFFarmerBE.FarmerBE.requests.LoginInput;
import NHHFFarmerBE.FarmerBE.services.FarmerService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FarmerController {
    
    @Autowired
    public final FarmerService farmerService;

    public FarmerController(FarmerService farmerService){
        this.farmerService = farmerService;
    }

    // ADD A FARMER

    @PostMapping("/farmer")
    public ResponseEntity<SignupResponse> createFarmer(@RequestBody CreateFarmerInput createFarmerInput) {
        SignupResponse response = new SignupResponse("", false);
        
        Farmer farmer = farmerService.create(createFarmerInput.toFarmer());
        if (farmer != null) {
            response = new SignupResponse(Integer.toString(farmer.getId()), true);
        }

        return new ResponseEntity<SignupResponse>(response, HttpStatus.OK);
    }
    

    // GET ALL FARMERS

    @GetMapping("farmer")
    public ResponseEntity<List<Farmer>> allTasks() {
        List<Farmer> FarmerList = farmerService.findAll();
        return new ResponseEntity<>(FarmerList, HttpStatus.OK);
    }


    //  GET FARMER BY ID 

    @GetMapping("farmerFull/{id}")
    public ResponseEntity<Farmer> getFarmerByIDFULL(
        @RequestHeader("token") String token, 
        @PathVariable int id) {

        VerifyHandler handler = new VerifyHandler(this.farmerService);
        handler.verify(token);
            
        if(handler.isSuccess() && handler.getRole() == "admin") {
            return new ResponseEntity<>(handler.getFarmer(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("farmerLight/{username}")
    public ResponseEntity<GetFarmerLightResponse> getFarmerByIDLIGHT(@PathVariable String username) {
        Optional<Farmer> Optionalfarmer = farmerService.findByUsername(username);
        Farmer farmer;


        if (Optionalfarmer.isPresent()) {
            farmer = Optionalfarmer.get();
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GetFarmerLightResponse response = new GetFarmerLightResponse(
            String.valueOf(farmer.getId()), 
            farmer.getUsername(), 
            farmer.getImage(), 
            farmer.getArea(), 
            farmer.getAddress());

        return new ResponseEntity<GetFarmerLightResponse>(response, HttpStatus.OK);

    }

    // DELETE A FARMER BY ID

    @DeleteMapping("/farmer/{id}")
    public ResponseEntity<Void> deleteFarmer(@PathVariable int id) {
        farmerService.delete(id);

        return ResponseEntity.noContent().build();
    }


    // FIND FARMER BY AREA
    @GetMapping("farmer/areas")
    public ResponseEntity<AreaPageProductResponse> getFarmerByAreaPage(@RequestParam String area, int page) {
        int pageSize = 12;
        List<Farmer> farmerList = farmerService.findByArea(area);

        if (farmerList.isEmpty()){
            return new ResponseEntity<AreaPageProductResponse>(null, null, HttpStatus.NOT_FOUND);
        }

        List<Farmer> SubList;
        int totalPageNumber = (int)Math.round((farmerList.size()/pageSize)+0.5);

        //int totalPageNumber = (int)totalPageNumbertemp;

        if (page > totalPageNumber){
            AreaPageProductResponse res = new AreaPageProductResponse(farmerList, pageSize, totalPageNumber);
            return new ResponseEntity<AreaPageProductResponse>(res, HttpStatus.NOT_ACCEPTABLE);
        }
        
        if ((page == totalPageNumber) && (farmerList.size() % pageSize != 0)){
            SubList = farmerList.subList((page-1) * pageSize, page * pageSize - (page * pageSize - (farmerList.size() % pageSize)));
        } else{
            SubList = farmerList.subList((page-1) * pageSize, page * pageSize);
        }
        AreaPageProductResponse response = new AreaPageProductResponse(SubList, page, totalPageNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
        }


    @PostMapping("/farmer/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginInput email){
        
        String mail = email.ToStringEmail();
        Farmer farmer = farmerService.findByEmail(mail);
        LoginResponse response = new LoginResponse("", "", "", false); 
        if(farmer != null) {
            response = new LoginResponse(
                farmer.getPassword(), 
                Integer.toString(farmer.getId()),
                farmer.getUsername(), 
                true
            ); 
        }
        return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);

    }
}

