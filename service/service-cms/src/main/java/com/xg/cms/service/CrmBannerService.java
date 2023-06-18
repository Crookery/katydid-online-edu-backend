package com.xg.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xg.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xg.cms.entity.vo.BannerQuery;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author katydid
 * @since 2023-03-27
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 【前台】查询所有Banner
     */
    List<CrmBanner> getAllBanner();

    /**
     * 【后台】条件分页查询
     */
    void pageQuery(Page<CrmBanner> bannerPage, BannerQuery bannerQuery);

}
