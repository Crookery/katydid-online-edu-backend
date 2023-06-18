package com.xg.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token管理
 */
@Component
public class TokenManager {

    /**
     * token密钥
     */
    private long tokenExpiration = 24*60*60*1000;      //过期时间

    /**
     * 编码密钥(签名)
     */
    private String tokenSignKey = "katydid";

    /**
     * 根据用户名，生成密钥
     * @param username 用户名
     * @return JWT设置username 过期日期  Hash签名后的token
     */
    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    /**
     * 根据 token 获取用户名
     * @return 解密token后的用户名
     */
    public String getUserFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 删除token
    public void removeToken(String token) {
        //此处没有必要，客户端不携带即可
    }

}
