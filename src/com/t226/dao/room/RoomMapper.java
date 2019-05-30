package com.t226.dao.room;

import com.t226.pojo.Room;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomMapper {
    //条件查询房间
    List<Room> getRoomList(@Param("thisPage") int thisPage, @Param("pageSize") int pageSize, @Param("addressId") String addressId, @Param("userId") String userId, @Param("buildingId") String buildingId, @Param("layerId") String layerId);
    //条件查询数量
    int getRoomCount(@Param("addressId") String addressId, @Param("userId") String userId, @Param("buildingId") String buildingId, @Param("layerId") String layerId);
    Room getRoom(int roomId);//根据房间id查询

    //条件查询房间1
    List<Room> getRoomList1(@Param("addressId") String addressId, @Param("userId") String userId, @Param("buildingId") String buildingId, @Param("layerId") String layerId);

    //购房操作
    int purchaseRoom(@Param("stopTime") String stopTime,@Param("money") double money,@Param("roomId") int roomId,@Param("userId") int userId);

    //购买后更新房子主人
    int updateRoomUserId(@Param("userId") int userId,@Param("id") int id);
}
