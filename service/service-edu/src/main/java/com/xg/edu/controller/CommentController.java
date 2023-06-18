package com.xg.edu.controller;


import com.xg.commonutils.Message;
import com.xg.edu.client.UCenterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author katydid
 * @since 2023-04-04
 */


@RestController
@RequestMapping("/edu/comment")
public class CommentController {

    @Autowired
    private UCenterClient uCenterClient;

}

