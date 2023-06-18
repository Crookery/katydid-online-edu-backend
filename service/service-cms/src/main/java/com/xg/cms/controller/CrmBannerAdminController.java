package com.xg.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xg.cms.entity.CrmBanner;
import com.xg.cms.entity.vo.BannerQuery;
import com.xg.cms.service.CrmBannerService;
import com.xg.commonutils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 后台页面的前端控制器
 * </p>
 *
 * @author katydid
 * @since 2023-03-27
 */

@RestController
@RequestMapping("/cms/bannerAdmin")
public class CrmBannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    /**
     * 条件分页查询banner
     */
    @PostMapping("/pageBanner/{page}/{limit}")
    public Message pageBanner(@PathVariable Long page, @PathVariable Long limit,
                              @RequestBody(required = false) BannerQuery bannerQuery){
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        crmBannerService.pageQuery(bannerPage,bannerQuery);
        // 获取数据
        List<CrmBanner> list = bannerPage.getRecords();
        // 获取总记录数
        long total = bannerPage.getTotal();
        return Message.successful().add("rows",list).add("total",total);
    }

    // 添加banner
    @PostMapping("/addBanner")
    public Message addBanner(@RequestBody CrmBanner crmBanner){
        boolean flag = crmBannerService.save(crmBanner);
        if (flag){
            return Message.successful();
        }else {
            return Message.fail();
        }
    }

    // 修改banner
    @PostMapping("/updateBanner")
    public Message updateBanner(@RequestBody CrmBanner crmBanner){
        boolean flag = crmBannerService.updateById(crmBanner);
        if (flag){
            return Message.successful();
        }else {
            return Message.fail();
        }
    }

    // 根据id删除banner
    @DeleteMapping("/deleteBannerById/{id}")
    public Message deleteBannerById(@PathVariable String id){
        boolean flag = crmBannerService.removeById(id);
        if (flag){
            return Message.successful();
        }else {
            return Message.fail();
        }
    }

    // 根据id查询banner
    @GetMapping("/getBannerById/{id}")
    public Message getBannerById(@PathVariable String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return Message.successful().add("item",crmBanner);
    }

}

