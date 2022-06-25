package com.linyonghao.oss.manager.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linyonghao.oss.common.config.SystemConfig;
import com.linyonghao.oss.common.dto.TemporaryUpDownCacheInfo;
import com.linyonghao.oss.common.entity.CoreBucket;
import com.linyonghao.oss.common.entity.CoreDomain;
import com.linyonghao.oss.common.entity.CoreObject;
import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.common.service.ICoreBucketService;
import com.linyonghao.oss.common.service.ICoreDomainService;
import com.linyonghao.oss.common.service.ICoreObjectService;
import com.linyonghao.oss.common.service.impl.TemporaryUpDownRedisService;
import com.linyonghao.oss.common.utils.StringUtil;
import com.linyonghao.oss.manager.Constant.PageConstant;
import com.linyonghao.oss.manager.annotation.OssCheckBucket;
import com.linyonghao.oss.manager.dto.BucketStatistic;
import com.linyonghao.oss.manager.entity.JSONResponse;
import com.linyonghao.oss.manager.service.StatisticService;
import com.linyonghao.oss.manager.utils.JSONResponseUtil;
import com.linyonghao.oss.manager.utils.RegexValidateUtil;
import com.linyonghao.oss.manager.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("space")
public class SpaceController {

    @Autowired
    private ICoreBucketService coreBucketService;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private ICoreObjectService coreObjectService;

    @Autowired
    private ICoreDomainService domainService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    TemporaryUpDownRedisService temporaryUpDownRedisService;


    /**
     * 添加页面
     * @return
     */
    @SaCheckPermission({"bucket-add"})
    @GetMapping("new")
    public ModelAndView newViewGET(HttpServletRequest request) {
        return ResponseUtil.view("space/new",request,null);
    }

    /**
     * 添加空间
     * @param name
     * @param control
     * @return
     */
    @SaCheckPermission({"bucket-add"})
    @PostMapping("new")
    public ModelAndView addSpace(String name, String control, HttpServletRequest request) {
        //名称格式为 3 ~ 63 个字符，可以包含小写字母、数字、短划线，且必须以小写字母或者数字开头和结尾。
        // 查重
        HashMap<String, Object> sessionMap = new HashMap<>();
        if (name.length() <= 3 || name.length() >= 63 || !RegexValidateUtil.customCheck("^[0-9a-zA-Z_]{1,}$", name)) {
            return ResponseUtil.error("space/new", sessionMap, "空间名不符合规范");
        }
        sessionMap.put("name",name);
        QueryWrapper<CoreBucket> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        long count = coreBucketService.count(wrapper);
        if (count > 0) {
            return ResponseUtil.error("space/new", sessionMap, "存在同名的空间");
        }

        CoreBucket bucket = new CoreBucket();
        bucket.setName(name);
        bucket.setAc(control.equals("public") ? 0 : 1);
        bucket.setUserId(Long.parseLong(StpUtil.getLoginId().toString()));
        coreBucketService.save(bucket);
        return ResponseUtil.success("redirect:/space/index",null,"添加成功");
    }

    @GetMapping({"index",""})
    public ModelAndView spaceInfo(Integer page){
       if(page == null){
           page = 1;
       }
        PageHelper.startPage(page, PageConstant.EVERY_PAGE_NUM);
        QueryWrapper<CoreBucket> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",StpUtil.getLoginId());
        List<CoreBucket> resultList = coreBucketService.list(wrapper);
        PageInfo<CoreBucket> pageList = new PageInfo<>(resultList);
        System.out.println(pageList);
        HashMap<String, Object> model = new HashMap<>();
        model.put("bucket_info", pageList);
        return ResponseUtil.view("space/index",model);
    }

    @OssCheckBucket
    @GetMapping("/{bucketId}/view")
    public ModelAndView spaceView(@PathVariable("bucketId") String bucketId){
        // 存储量 对象个数 API请求数 PUT/GET  域名个数
        CoreBucket bucketInfo = coreBucketService.getById(bucketId);
        long[] result = coreObjectService.getObjectNumAndSizeSumByBucket(StpUtil.getLoginId().toString(), bucketId);
        long objNum = result[0];
        long objSize = result[1];

        long getCount = coreBucketService.getThisMonthGETCount(bucketId);
        long postCount = coreBucketService.getThisMonthPOSTCount(bucketId);

        List<CoreDomain> domainInfos = domainService.getByBucketID(bucketId);
        UserModel userInfo = (UserModel) StpUtil.getSession().get("user_info");
        for (CoreDomain domainInfo : domainInfos) {
            domainInfo.setCNAME(String.format("%s-%s.%s",bucketId,userInfo.getUsername() ,systemConfig.domain));
        }

        HashMap<String, Object> model = new HashMap<>();
        model.put("object_num", Long.toString(objNum));
        model.put("object_size", StringUtil.formatByteSize(objSize));
        model.put("get_count", Long.toString(getCount));
        model.put("post_count", Long.toString(postCount));
        model.put("bucket_info",bucketInfo);
        model.put("domain_infos",domainInfos);

        return ResponseUtil.view("space/view",model);
    }
    @OssCheckBucket
    @GetMapping("/{bucketId}/api/statistic")
    @ResponseBody
    public JSONResponse bucketStatistic(@PathVariable("bucketId") String bucketId){
        BucketStatistic bucketStatistic = statisticService.getBeforeNDayBucketInfoLimitDay(bucketId, -6);
        return JSONResponseUtil.success(bucketStatistic);
    }

    @OssCheckBucket
    @GetMapping("/{bucketId}/file")
    public ModelAndView bucketFileGET(@PathVariable("bucketId") String bucketId,Integer page){
        if(page == null){
            page = 1;
        }
        PageHelper.startPage(page,PageConstant.EVERY_PAGE_NUM);
        QueryWrapper<CoreBucket> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",StpUtil.getLoginId());
        List<CoreObject> coreObjects = coreObjectService.getObjectByBucketId(bucketId);

        HashMap<String, Object> model = new HashMap<>();
        PageInfo<CoreObject> coreObjectPageInfo = new PageInfo<>(coreObjects);
        model.put("file_list",coreObjectPageInfo);
        model.put("bucket_id",bucketId);

        return ResponseUtil.view("/space/file",model);
    }


    @OssCheckBucket
    @GetMapping("/{bucketId}/api/token")
    @ResponseBody
    public JSONResponse uploadInfo(@PathVariable("bucketId") String bucketId){
        String token = UUID.randomUUID().toString().replace("-", "");
        temporaryUpDownRedisService.set(new TemporaryUpDownCacheInfo(bucketId,token,StpUtil.getLoginId().toString(),
                systemConfig.temporaryUpDownExpired));
        return JSONResponseUtil.success(token);
    }



}
