package com.t226.service.room;

import com.t226.pojo.Room;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomService {
    //查询房间
    List<Room> getRoomList(int thisPage,int pageSize,String addressId,String userId,String buildingId,String layerId);
    //条件查询数量
    int getRoomCount(String addressId,String userId,String buildingId,String layerId);
    Room getRoom(int roomId);//根据房间id查询

    //查询房间1
    List<Room> getRoomList1(String addressId,String userId,String buildingId,String layerId);

    //购房操作
    int purchaseRoom(String stopTime,double money,int roomId,int userId);


    //购买后更新房子主人
    int updateRoomUserId(@Param("userId") int userId,@Param("id") int id);
}
