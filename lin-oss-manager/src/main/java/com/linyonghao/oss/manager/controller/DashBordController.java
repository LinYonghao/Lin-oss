package com.linyonghao.oss.manager.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.linyonghao.influxdb2.entity.CountWithTime;
import com.linyonghao.oss.manager.entity.JSONResponse;
import com.linyonghao.oss.manager.service.StatisticService;
import com.linyonghao.oss.manager.utils.JSONResponseUtil;
import com.linyonghao.oss.manager.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("dashbord")
public class DashBordController {
    @Autowired
    StatisticService statisticService;

    /**
     * 数据面板
     * @return 数据面板页面
     */
    @GetMapping("")
    public ModelAndView get(){
        Map<String, Object> params = new HashMap<>();
        String id = (String) StpUtil.getLoginId();
        params.put("today_api_num",String.valueOf(statisticService.getAPITodayNumById(id)));
        params.put("space_num", String.valueOf(statisticService.getSpaceNumById(id)));
        params.put("object_num", String.valueOf(statisticService.getObjectNumById(id)));
        params.put("space_infos",statisticService.getSpaceInfoById(id));
        return ResponseUtil.view("dashbords",params);
    }


    /**
     * 获取今天的API访问次数
     * @return JSON
     */
    @SaCheckLogin
    @GetMapping("/api/todayApiNum")
    @ResponseBody
    public JSONResponse todayApiNum(){
        String[] xLabel = new String[24];
        for (int i = 0; i < 24; i++) {
            if(i < 10){
                xLabel[i] = String.format("%s:00", "0" + i);
            }else{
                xLabel[i] = String.format("%s:00",  i);
            }
        }
        // 00:00","01:00
        List<Long> yRet = new ArrayList<>();
        List<CountWithTime> todayAPIData = statisticService.getTodayAPINumById(StpUtil.getLoginId().toString(), "1h");
        for (CountWithTime item : todayAPIData) {
            yRet.add(item.getCount());
        }


        HashMap<String, Object> ret = new HashMap<>();
        ret.put("x", xLabel);
        ret.put("y", yRet);
        return JSONResponseUtil.success(ret);
    }




}
