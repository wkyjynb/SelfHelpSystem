package com.t226.controller.building;

import com.alibaba.fastjson.JSON;
import com.t226.service.building.BuildingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/building")
public class BuildingController {
    @Resource
    private BuildingService buildingService;

    //使用REST风格
/*    @RequestMapping(value = "/getLayer/{id}")
    public String getLayer(@PathVariable int id, HttpServletRequest request){
        System.out.println("------------------------------------------->");
        request.setAttribute("LayerList",buildingService.getBuilding(id));
        return "Layer";
    }*/

    //根据父id和区域id进行联动查询
    @ResponseBody
    @RequestMapping("/getBuilding")
    public Object getBuilding(int parentId,int addressId){
        return  JSON.toJSONString(buildingService.getBuilding(parentId,addressId));
   }


}
