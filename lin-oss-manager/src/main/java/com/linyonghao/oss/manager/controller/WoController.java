package com.linyonghao.oss.manager.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.linyonghao.oss.manager.entity.CoreWo;
import com.linyonghao.oss.manager.entity.CoreWoRecord;
import com.linyonghao.oss.manager.service.ICoreWoRecordService;
import com.linyonghao.oss.manager.service.ICoreWoService;
import com.linyonghao.oss.manager.utils.ResponseUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("wo")
public class WoController {
    @Autowired
    private ICoreWoService woService;

    @Autowired
    private ICoreWoRecordService woRecordService;



    @GetMapping("my")
    public ModelAndView myGET(){
        List<CoreWo> coreWoList = woService.getAllWoByUserId((String) StpUtil.getLoginId());
        HashMap<String, Object> model = new HashMap<>();
        model.put("wo_list",coreWoList);

        return ResponseUtil.view("wo/my",model);
    }

    @GetMapping("{woId}/detail")
    public ModelAndView detail(@PathVariable("woId")String woId, HttpServletRequest request){

        List<CoreWoRecord> recordList = woRecordService.getRecordList(woId);
        CoreWo wo = woService.getOne(woId);
        HashMap<String, Object> model = new HashMap<>();
        model.put("wo",wo);
        model.put("record_list",recordList);
        return ResponseUtil.view("wo/detail",request,model);
    }
    @GetMapping("new")
    public ModelAndView newGET(){
        return ResponseUtil.view("wo/new",null);
    }

    @PostMapping("new")
    public ModelAndView newPOST(CoreWo wo){
        wo.setCreateTime(new Date());
        wo.setUserId(Long.parseLong(StpUtil.getLoginId().toString()));
        wo.setStatus(0);
        woService.createWo(wo);
        return ResponseUtil.success("redirect:/wo/my",null,"添加成功");
    }

    @SaCheckRole("super-admin")
    @GetMapping("distribute")
    public ModelAndView distributeGET(){
        List<CoreWo> distribute = woService.getDistribute();
        HashMap<String, Object> model = new HashMap<>();
        model.put("wo_list",distribute);
        return ResponseUtil.view("/wo/admin/distribute",model);
    }

    @SaCheckRole("super-admin")
    @GetMapping("{woId}/process")
    public ModelAndView process(@PathVariable("woId") String woId){
        woService.removeOneDistribute(woId);
        woService.insertToPending(StpUtil.getLoginId().toString(),woId);
        return ResponseUtil.view("redirect:/wo/" + woId + "/detail",null);
    }

    @SaCheckRole("super-admin")
    @GetMapping("pending")
    public ModelAndView pending(){
        List<CoreWo> pending = woService.getPending(StpUtil.getLoginId().toString());
        HashMap<String, Object> model = new HashMap<>();
        model.put("wo_list",pending);
        return ResponseUtil.view("wo/my",model);
    }


    @PostMapping("{woId}/reply")
    public ModelAndView reply(@PathVariable("woId") String woId,String message){
        CoreWoRecord coreWoRecord = new CoreWoRecord();
        coreWoRecord.setId(IdWorker.getId());
        coreWoRecord.setWoId(Long.valueOf(woId));
        coreWoRecord.setPublishTime(new Date());
        if (StpUtil.hasRole("super-admin")) {
            coreWoRecord.setAdminId(Long.parseLong(StpUtil.getLoginId().toString()));
        }
        coreWoRecord.setMessage(message);
        woRecordService.insertOne(coreWoRecord);
        return ResponseUtil.view("redirect:/wo/" + woId + "/detail",null);
    }











}
