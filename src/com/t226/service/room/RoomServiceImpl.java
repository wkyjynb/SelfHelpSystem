package com.t226.service.room;

import com.t226.dao.room.RoomMapper;
import com.t226.pojo.Room;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class RoomServiceImpl implements RoomService{

    @Resource
    private RoomMapper roomMapper;

    //查询房间
    @Override
    public List<Room> getRoomList(int thisPage,int pageSize,String addressId,String userId,String buildingId,String layerId) {
        return roomMapper.getRoomList((thisPage-1)*pageSize,pageSize,addressId,userId,buildingId,layerId);
    }

    //条件查询数量
    @Override
    public int getRoomCount(String addressId, String userId, String buildingId, String layerId) {
        return roomMapper.getRoomCount(addressId,userId,buildingId,layerId);
    }
//根据房间id查询
    @Override
    public Room getRoom(int roomId) {
        return roomMapper.getRoom(roomId);
    }

    @Override
    public List<Room> getHouse(int id) {
        return roomMapper.getHouse(id);
    }
}
