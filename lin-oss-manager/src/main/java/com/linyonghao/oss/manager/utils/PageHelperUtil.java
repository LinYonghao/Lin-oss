package com.linyonghao.oss.manager.utils;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

public class PageHelperUtil {
    /**
     * pagehelper手动分页
     * @param currentPage 当前页
     * @param pageSize
     * @param list
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> getPageInfo(int currentPage, int pageSize, List<T> list) {
        int total = list.size();
        if (total > pageSize) {
            int toIndex = pageSize * currentPage;
            if (toIndex > total) {
                toIndex = total;
            }
            if (pageSize * (currentPage - 1)<toIndex) {
                list = list.subList(pageSize * (currentPage - 1), toIndex);
            }
            else {
                list=new ArrayList<>();
            }
        }
        Page<T> page = new Page<>(currentPage, pageSize);
        page.addAll(list);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);

        return new PageInfo<>(page);
    }
}
