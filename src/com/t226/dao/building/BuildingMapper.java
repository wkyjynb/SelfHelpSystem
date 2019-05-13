package com.t226.dao.building;

import com.t226.pojo.Building;
import com.t226.pojo.Room;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BuildingMapper {
    List<Building> getBuilding(@Param("parentId") int parentId,@Param("addressId") int addressId);//根据父id和区域id进行联动查询

}
