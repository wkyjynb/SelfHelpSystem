package com.t226.service.building;

import com.t226.dao.building.BuildingMapper;
import com.t226.pojo.Building;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class BuildingServiceImpl implements BuildingService{

    @Resource
    private BuildingMapper buildingMapper;

    //根据父id和区域id进行联动查询
    @Override
    public List<Building> getBuilding(int parentId,int addressId) {
        return buildingMapper.getBuilding(parentId,addressId);
    }
}
