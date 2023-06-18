package com.xg.ucenter.service;

import com.xg.commonutils.Message;
import com.xg.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xg.ucenter.entity.vo.LoginVo;
import com.xg.ucenter.entity.vo.RegisterVo;
import com.xg.ucenter.entity.vo.ShowVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author katydid
 * @since 2023-04-02
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    ShowVo getLoginInfo(String id);

    Member getOpenIdMember(String openid);

    Integer countRegisters(String day);

    /**
     * 为用户新增邮箱
     * @param uid controller已验证过
     * @param password 绑定邮箱
     * @param email 需要新增邮箱
     * @param code 发送的二维码
     * @return 是否添加成功
     */
    Message confirmEmail(String uid, String email, String password,String code);
}
