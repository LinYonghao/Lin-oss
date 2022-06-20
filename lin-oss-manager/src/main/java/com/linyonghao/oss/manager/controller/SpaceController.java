package com.linyonghao.oss.manager.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linyonghao.oss.common.dao.mapper.relationship.CoreBucketMapper;
import com.linyonghao.oss.common.entity.CoreBucket;
import com.linyonghao.oss.common.service.ICoreBucketService;
import com.linyonghao.oss.common.service.ICoreObjectService;
import com.linyonghao.oss.manager.Constant.PageConstant;
import com.linyonghao.oss.manager.annotation.OssCheckBucket;
import com.linyonghao.oss.manager.service.StatisticService;
import com.linyonghao.oss.manager.utils.RegexValidateUtil;
import com.linyonghao.oss.manager.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("space")
public class SpaceController {

    @Autowired
    private ICoreBucketService coreBucketService;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private ICoreObjectService coreObjectService;


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

        HashMap<String, Object> model = new HashMap<>();
        model.put("object_num", Long.toString(objNum));
        model.put("object_size", Long.toString(objSize));
        model.put("get_count", Long.toString(getCount));
        model.put("post_count", Long.toString(postCount));
        model.put("bucket_info",bucketInfo);

        return ResponseUtil.view("space/view",model);
    }


}
