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
import com.linyonghao.oss.common.entity.DirectoryTree;
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
import com.linyonghao.oss.manager.utils.PageHelperUtil;
import com.linyonghao.oss.manager.utils.RegexValidateUtil;
import com.linyonghao.oss.manager.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reactor.util.annotation.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     *
     * @return
     */
    @GetMapping("new")
    public ModelAndView newViewGET(HttpServletRequest request) {
        return ResponseUtil.view("space/new", request, null);
    }

    /**
     * 添加空间
     *
     * @param name
     * @param control
     * @return
     */
    @PostMapping("new")
    public ModelAndView addSpace(String name, String control, HttpServletRequest request) {
        //名称格式为 3 ~ 63 个字符，可以包含小写字母、数字、短划线，且必须以小写字母或者数字开头和结尾。
        // 查重
        HashMap<String, Object> sessionMap = new HashMap<>();
        if (name.length() <= 3 || name.length() >= 63 || !RegexValidateUtil.customCheck("^[0-9a-zA-Z_]{1,}$", name)) {
            return ResponseUtil.error("space/new", sessionMap, "空间名不符合规范");
        }
        sessionMap.put("name", name);
        QueryWrapper<CoreBucket> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        wrapper.eq("user_id", StpUtil.getLoginId().toString());
        long count = coreBucketService.count(wrapper);
        if (count > 0) {
            return ResponseUtil.error("space/new", sessionMap, "存在同名的空间");
        }

        CoreBucket bucket = new CoreBucket();
        bucket.setName(name);
        bucket.setAc(control.equals("public") ? 0 : 1);
        bucket.setUserId(Long.parseLong(StpUtil.getLoginId().toString()));
        coreBucketService.save(bucket);
        return ResponseUtil.success("redirect:/space/index", null, "添加成功");
    }

    @GetMapping({"index", ""})
    public ModelAndView spaceInfo(Integer page) {
        if (page == null) {
            page = 1;
        }
        PageHelper.startPage(page, PageConstant.EVERY_PAGE_NUM);
        QueryWrapper<CoreBucket> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", StpUtil.getLoginId());
        List<CoreBucket> resultList = coreBucketService.list(wrapper);
        PageInfo<CoreBucket> pageList = new PageInfo<>(resultList);
        System.out.println(pageList);
        HashMap<String, Object> model = new HashMap<>();
        model.put("bucket_info", pageList);
        return ResponseUtil.view("space/index", model);
    }

    @OssCheckBucket
    @GetMapping("/{bucketId}/view")
    public ModelAndView spaceView(@PathVariable("bucketId") String bucketId) {
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
            domainInfo.setCNAME(String.format("%s-%s.%s", bucketId, userInfo.getUsername(), systemConfig.domain));
        }

        HashMap<String, Object> model = new HashMap<>();
        model.put("object_num", Long.toString(objNum));
        model.put("object_size", StringUtil.formatByteSize(objSize));
        model.put("get_count", Long.toString(getCount));
        model.put("post_count", Long.toString(postCount));
        model.put("bucket_info", bucketInfo);
        model.put("domain_infos", domainInfos);

        return ResponseUtil.view("space/view", model);
    }

    @OssCheckBucket
    @GetMapping("/{bucketId}/api/statistic")
    @ResponseBody
    public JSONResponse bucketStatistic(@PathVariable("bucketId") String bucketId) {
        BucketStatistic bucketStatistic = statisticService.getBeforeNDayBucketInfoLimitDay(bucketId, -6);
        return JSONResponseUtil.success(bucketStatistic);
    }

    /**
     * 获取根目录
     *
     * @param bucketId
     * @param page
     * @return
     */
    @OssCheckBucket
    @GetMapping({"/{bucketId}/file"})
    public ModelAndView bucketFileGET(@PathVariable("bucketId") String bucketId, Integer page) {
        return ResponseUtil.view("/space/file", getFileList(page, bucketId, null));
    }

    /**
     * 获取指定目录
     *
     * @param bucketId
     * @param page
     * @return
     */
    @OssCheckBucket
    @GetMapping({"/{bucketId}/file/**"})
    public ModelAndView bucketFileGET(@PathVariable("bucketId") String bucketId, Integer page, HttpServletRequest request) {
        Matcher matcher = Pattern.compile("space/\\d+/file/(.*)").matcher(request.getServletPath());
        matcher.find();
        String dir = "";
        if (matcher.groupCount() == 1) {
            dir = matcher.group(1);
        } else {
            ResponseUtil.error("/space/file", null, "找不到该目录");
        }
        Map<String, Object> model = getFileList(page, bucketId, dir);

        if ( model.get("is_empty") == null || (Boolean) model.get("is_empty")) {
            return ResponseUtil.error("/space/file", model, "找不到该目录");
        }

        return ResponseUtil.view("/space/file", model);
    }

    public Map<String, Object> getFileList(Integer page, String bucketId, @Nullable String dir) {
        if (page == null) {
            page = 1;
        }

        DirectoryTree directoryTree;
        if (dir != null) {
            directoryTree = coreObjectService.getDirTreeByDir(String.format("/%s", dir), bucketId);
        } else {
            directoryTree = coreObjectService.getDirTreeByDir(null, bucketId);
        }


        HashMap<String, Object> model = new HashMap<>();
        PageInfo<CoreObject> coreObjectPageInfo = PageHelperUtil.getPageInfo(page, PageConstant.EVERY_PAGE_NUM, directoryTree.getFiles());
        String token = temporaryUpDownRedisService.generateOneKey(bucketId, StpUtil.getLoginId().toString());
        String currentDir =  dir == null ? "" : "/" + dir;
        ArrayList<String>  dirNames = new ArrayList<>();
        String curDir = "";
        for (String s : currentDir.split("/")) {
            curDir += s + "/";
            dirNames.add(curDir);
        }
        // TODO 必须重构 路径信息乱
        model.put("file_list", coreObjectPageInfo);
        model.put("bucket_id", bucketId);
        model.put("temporary_token", token);
        model.put("base_url", systemConfig.baseUrl + "/download/");
        model.put("is_empty", directoryTree.getFiles().size() == 0);
        model.put("dirs", directoryTree.getDirectory());
        model.put("dirNames", dirNames); // 以分段类型显示目录，方便前端显示数据
        model.put("current_dir", currentDir);
        model.put("token", token);
        return model;

    }

    @OssCheckBucket
    @GetMapping("/{bucketId}/api/token")
    @ResponseBody
    public JSONResponse uploadInfo(@PathVariable("bucketId") String bucketId) {

        HashMap<String, String> res = new HashMap<>();
        res.put("token", temporaryUpDownRedisService.generateOneKey(bucketId, StpUtil.getLoginId().toString()));
        res.put("base_url", systemConfig.baseUrl);
        return JSONResponseUtil.success(res);
    }


}
