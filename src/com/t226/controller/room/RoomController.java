package com.t226.controller.room;

import com.mysql.jdbc.StringUtils;
import com.t226.pojo.Room;
import com.t226.pojo.User;
import com.t226.service.address.AddressService;
import com.t226.service.building.BuildingService;
import com.t226.service.room.RoomService;
import com.t226.tools.Constants;
import com.t226.tools.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Resource
    private RoomService roomService;

    @Resource
    private AddressService addressService;

    @Resource
    private BuildingService buildingService;


    //条件查询房间
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
        page.setThisPage(thisIndex);
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






   /* //购买页面
    @RequestMapping(value = "purchase1")
    public String purchase1(){
        return "/user/appversionadd";
    }*/

    /**
     * 使用REST风格，需要使用@PathVariable注解进行标注
     * 购房页面
     */
    @RequestMapping(value = "/purchase/{id}",method= RequestMethod.GET)
    public String purchase2(@PathVariable int id,HttpServletRequest request){
      request.setAttribute("room",roomService.getRoom(id));
       return "/user/purchase";
    }
    @RequestMapping(value = "/myHouse.html")
    public String myHouse(HttpServletRequest request, HttpSession session){
        System.out.println("进入----------------------------------");
        List<Room> houseList=roomService.getHouse(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        request.setAttribute("houseList",houseList);
        return "/user/MyHouse";
    }










}
