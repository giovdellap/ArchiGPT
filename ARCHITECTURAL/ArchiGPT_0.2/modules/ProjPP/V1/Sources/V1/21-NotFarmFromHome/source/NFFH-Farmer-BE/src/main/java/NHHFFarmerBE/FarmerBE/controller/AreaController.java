package NHHFFarmerBE.FarmerBE.controller;

import NHHFFarmerBE.FarmerBE.entities.Area;
import NHHFFarmerBE.FarmerBE.models.verifytoken.VerifyHandler;
import NHHFFarmerBE.FarmerBE.repositories.FarmerRepository;
import NHHFFarmerBE.FarmerBE.requests.CreateAreaInput;
import NHHFFarmerBE.FarmerBE.services.AreaService;
import NHHFFarmerBE.FarmerBE.services.FarmerService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AreaController {
    
    @Autowired
    public final AreaService areaService;
    public final FarmerService farmerService;
    public final FarmerRepository farmerRepository;

    public AreaController(
        AreaService areaService, 
        FarmerService farmerService,
        FarmerRepository farmerRepository){
        this.areaService = areaService;
        this.farmerService = farmerService;
        this.farmerRepository = farmerRepository;
    }

    //NEW AREA

    @PostMapping("/area")
    public ResponseEntity<Area> createArea(
        @RequestHeader("token") String token, 
        @RequestBody CreateAreaInput createTaskInput) {

        VerifyHandler handler = new VerifyHandler(this.farmerService);
        handler.verify(token);
            
        if(handler.isSuccess() && handler.getRole() != "admin") {
            Area createdArea = areaService.create(createTaskInput.toArea());
            return new ResponseEntity<>(createdArea, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    //Return all areas

    @GetMapping("/area")
    public ResponseEntity<List<Area>> allAreas() {
        List<Area> AreaList = areaService.findAll();
        return new ResponseEntity<>(AreaList, HttpStatus.OK);
    }


    //Return areas in a particular page (Page size = 12)
    @GetMapping("/area/{page_number}")
    public ResponseEntity<List<Area>> getAreasPerPage(@PathVariable int page_number){
        int page_size = 3;
        
        List<Area> AreaList = areaService.findAll();
        
        List<Area> SubList = AreaList.subList(page_number * page_size, (page_number + 1) * page_size);

        return new ResponseEntity<>(SubList, HttpStatus.OK);
    }


    //Delete an Area using ID

    @DeleteMapping("/area/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable int id) {
        areaService.delete(id);

        return ResponseEntity.noContent().build();
    }


}
