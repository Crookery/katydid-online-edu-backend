package com.xg.security.security;


import com.xg.commonutils.Message;
import com.xg.commonutils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登出业务逻辑类：移除token
 */
public class TokenLogoutHandler implements LogoutHandler {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 1.从 Header 中获取token
        String token = request.getHeader("token");
        if (token != null) {
            // 2.删除(此处仅为演示步骤，实际实现客户端不传token即可)
            tokenManager.removeToken(token);
            // 3.获取username
            String userName = tokenManager.getUserFromToken(token);
            // 4.根据username 清空当前用户缓存中的权限数据
            redisTemplate.delete(userName);
        }
        ResponseUtil.out(response, Message.successful());
    }
}