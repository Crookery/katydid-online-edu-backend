package com.xg.cms.entity.vo;

import lombok.Data;

@Data
public class BannerQuery {
    private static final long serialVersionUID = 1L;

    /**
     * 幻灯片名字
     */
    private String name;

    /**
     * 开始日期-前端传递过来的日期为字符串
     */
    private String begin;

    /**
     * 结束日期
     */
    private String end;
}
