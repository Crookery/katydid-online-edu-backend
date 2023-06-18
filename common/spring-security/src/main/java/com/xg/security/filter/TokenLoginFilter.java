package com.xg.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xg.commonutils.Message;
import com.xg.commonutils.ResponseUtil;
import com.xg.security.entity.SecurityUser;
import com.xg.security.entity.User;
import com.xg.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 登录(认证)过滤器，继承重要过滤器 UsernamePasswordAuthenticationFilter
 * 重写三个方法
 *      attemptAuthentication(用户名密码进行登录校验)
 *      successfulAuthentication(认证成功处理过程)
 *      unsuccessfulAuthentication(认证失败处理过程)
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            // 获取表单提交过来的JSON数据
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登录(认证)成功的方法 => ①kv写redis；②放token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // 1.认证成功后得到认证成功后的用户信息 SecurityUser 继承的 框架的 UserDetails
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        // 2.生成token
        String token = tokenManager.createToken(user.getCurrentUserInfo().getUsername());
        // 3.username为k，权限列表为v，写入redis
        redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(), user.getPermissionValueList());
        // 4.token放Header
        ResponseUtil.out(res, Message.successful().add("token", token));
    }

    /**
     * 登录(认证)失败的方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(response, Message.fail());
    }
}
