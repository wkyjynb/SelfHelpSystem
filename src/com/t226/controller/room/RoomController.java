package com.t226.controller.room;

import com.mysql.jdbc.StringUtils;
import com.t226.service.address.AddressService;
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

    @RequestMapping("/RoomInfoList.html")
    public String RoomInfoList(HttpServletRequest request,@RequestParam(value = "thisPage",required = false) String thisPage,@RequestParam(value = "addressId",required = false) String addressId
                                    ,@RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "buildingId",required = false) String buildingId
                                    ,@RequestParam(value = "layerId",required = false) String layerId){
       int thisIndex=1;//当前页
       if(!StringUtils.isNullOrEmpty(thisPage)){
        thisIndex=Integer.parseInt(thisPage);
       }
        Page page=new Page();
       page.setPageSize(Constants.thisPage);
       page.setCount(roomService.getRoomCount(addressId,userId,buildingId,layerId));//查询数量
        System.out.println("----------------------------------------------->进入查询房间方法"+page.getCount());
        request.setAttribute("userId",userId);//当前选中区域id
       request.setAttribute("addressId",addressId);//当前选中区域id
        request.setAttribute("buildingId",buildingId);//当前选中楼号
        request.setAttribute("layerId",layerId);//当前选中楼层
        request.setAttribute("roomList",roomService.getRoomList(thisIndex,page.getPageSize(),addressId,userId,buildingId,layerId));//房间集合
       request.setAttribute("addressList",addressService.getAddressList());//区域集合
        return "/user/RoomInfoList";
    }
}
