package com.xg.commonutils;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class Message {
    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("状态码")
    private Integer code;

    @ApiModelProperty("返回消息")
    private String message;

    @ApiModelProperty("返回数据")
    private Map<String,Object> data = new HashMap<>();

    private Message(){}

    /**
     * 返回失败消息:5000
     */
    public static Message fail(){
        Message m = new Message();
        m.setCode(ResultCode.FAIL);
        m.setMessage("失败");
        m.setSuccess(false);
        return m;
    }

    /**
     * 成功消息:2000
     */
    public static Message successful(){
        Message m = new Message();
        m.setCode(ResultCode.SUCCESS);
        m.setMessage("成功");
        m.setSuccess(true);
        return m;
    }

    /**
     * 添加消息
     */
    public Message add(String key,Object data){
        this.getData().put(key,data);
        return this;
    }

    /**
     * 替换数据
     */
    public Message replace(Map<String,Object> map){
        this.setData(map);
        return this;
    }

    /**
     * 返回消息
     */
    public Message mes(String str){
        this.setMessage(str);
        return this;
    }

    /**
     * 修改响应码
     */
    public Message code(Integer code){
        this.setCode(code);
        return this;
    }

}
