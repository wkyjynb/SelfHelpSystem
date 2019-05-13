package com.t226.service.building;

import com.t226.pojo.Building;

import java.util.List;

public interface BuildingService {
    List<Building> getBuilding(int parentId,int addressId);//根据父id和区域id进行联动查询
}
