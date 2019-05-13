package com.t226.controller.room;

import com.mysql.jdbc.StringUtils;
import com.t226.service.address.AddressService;
import com.t226.service.building.BuildingService;
import com.t226.service.room.RoomService;
import com.t226.tools.Constants;
import com.t226.tools.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Resource
    private RoomService roomService;

    @Resource
    private AddressService addressService;

    @Resource
    private BuildingService buildingService;

    @RequestMapping("/RoomInfoList.html")
    public String RoomInfoList(HttpServletRequest request,@RequestParam(value = "thisPage",required = false) String thisPage,@RequestParam(value = "addressId",required = false) String addressId
                                    ,@RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "buildingId",required = false) String buildingId
                                    ,@RequestParam(value = "layerId",required = false) String layerId){
       int thisIndex=1;//当前页
       if(!StringUtils.isNullOrEmpty(thisPage)){
           System.out.println("没值0000000000000000000000000000000000000000000000000000000");
        thisIndex=Integer.parseInt(thisPage);
       }
        Page page=new Page();
       page.setPageSize(Constants.thisPage);
       page.setCount(roomService.getRoomCount(addressId,userId,buildingId,layerId));//查询数量
        page.setThisPage(thisIndex);
        System.out.println("----------------------------------------------->进入查询房间方法"+page.getCount());

      if(!StringUtils.isNullOrEmpty(addressId)){
          request.setAttribute("buildingList",buildingService.getBuilding(0,Integer.parseInt(addressId)));
          System.out.println(buildingService.getBuilding(0,Integer.parseInt(addressId)).size());
      }
      if(!StringUtils.isNullOrEmpty(addressId)&&!StringUtils.isNullOrEmpty(buildingId)){
          request.setAttribute("layerList",buildingService.getBuilding(Integer.parseInt(buildingId),Integer.parseInt(addressId)));
      }
        request.setAttribute("userId",userId);//当前选中区域id
       request.setAttribute("addressId",addressId);//当前选中区域id
        request.setAttribute("buildingId",buildingId);//回显当前选中楼号
       request.setAttribute("layerId",layerId);//回显当前选中楼层
        request.setAttribute("roomList",roomService.getRoomList(thisIndex,page.getPageSize(),addressId,userId,buildingId,layerId));//房间集合
       request.setAttribute("addressList",addressService.getAddressList());//区域集合
        request.setAttribute("pages",page);//分页
        return "/user/RoomInfoList";
    }
}
