package com.t226.controller.room;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import com.t226.pojo.Room;
import com.t226.service.address.AddressService;
import com.t226.service.building.BuildingService;
import com.t226.service.room.RoomService;
import com.t226.tools.Constants;
import com.t226.tools.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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




    @ResponseBody
    @RequestMapping(value = "/show.html")
    public Object show(HttpServletRequest request,@RequestParam(value = "addressId",required = false) String addressId
            ,@RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "buildingId",required = false) String buildingId
            ,@RequestParam(value = "layerId",required = false) String layerId){
        System.out.println("------------------------------------------------");
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
        request.setAttribute("addressList",addressService.getAddressList());//区域集合
        String json= JSON.toJSONString(roomService.getRoomList1(addressId,"0",buildingId,layerId));
        String json1=JSON.toJSONString(addressService.getAddressList());
        String json2=JSON.toJSONString(userId);
        String json3=JSON.toJSONString(addressId);
        String json4=JSON.toJSONString(buildingId);
        String json5=JSON.toJSONString(layerId);
        return json;
    }










    /*ds*/

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






    @ResponseBody
    @RequestMapping(value = "getAddressList")
    public Object getAddressList(){
        System.out.println(addressService.getAddressList());
        return JSON.toJSONString(addressService.getAddressList());
    }







}
