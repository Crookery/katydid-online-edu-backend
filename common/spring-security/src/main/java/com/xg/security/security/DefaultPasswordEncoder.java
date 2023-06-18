package com.xg.security.security;

import com.xg.commonutils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义密码加密，实现Spring Security 两个重要接口之一 PasswordEncoder
 * 使用 MD5 加密，而非官方推荐的 BCryptPasswordEncoder
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {


    public DefaultPasswordEncoder() {
        this(-1);
    }

    public DefaultPasswordEncoder(int strength) {

    }

    /**
     * 定义加密方法：MD5 加密
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    /**
     *  加密后与原(raw)密码匹配方式
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}