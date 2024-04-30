package NHHFFarmerBE.FarmerBE.requests;

import java.util.Date;

import NHHFFarmerBE.FarmerBE.entities.Area;


public record CreateAreaInput(String areaName) {
    public Area toArea() {
        Area area = new Area();

        area.setAreaName(areaName);

        return area;
    }
}