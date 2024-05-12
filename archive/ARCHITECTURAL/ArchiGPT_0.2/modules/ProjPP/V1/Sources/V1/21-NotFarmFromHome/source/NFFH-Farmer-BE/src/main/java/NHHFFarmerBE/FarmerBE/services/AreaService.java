package NHHFFarmerBE.FarmerBE.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NHHFFarmerBE.FarmerBE.entities.Area;
import NHHFFarmerBE.FarmerBE.repositories.AreaRepository;

@Service
public class AreaService implements IAreaService{
    
    @Autowired
    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository){
        this.areaRepository = areaRepository;
    }

    @Override
    public Area create(Area area){
        return this.areaRepository.save(area);
    }

    @Override
    public void delete(int id) {
        areaRepository.deleteById(id);
        return ;
    }

    @Override
    public List<Area> findAll() {
        List<Area> areaList = new ArrayList<>();
        this.areaRepository.findAll().forEach(areaList::add);

        return areaList;
    }
}
