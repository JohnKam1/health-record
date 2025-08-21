package com.Hootsybit.service.impl;

import com.Hootsybit.exception.ErrorCode;
import com.Hootsybit.exception.GlobalException;
import com.Hootsybit.pojo.entity.UserInfo;
import com.Hootsybit.service.UserInfoService;
import com.Hootsybit.service.WeChatService;
import com.Hootsybit.utils.JwtUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@Slf4j
public class WechatServiceImpl implements WeChatService {

    @Autowired
    private UserInfoService userInfoService;

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    private static final String WECHAT_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Override
    public String wechatLogin(String code) {
        // 使用临时code，获取用户唯一id
        String url = WECHAT_SESSION_URL + "?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String result = EntityUtils.toString(response.getEntity());
            log.info("微信登录返回信息: {}", result);

            // 如果需要转换为Map对象
            JSONObject jsonObject = JSON.parseObject(result);

            String openid = jsonObject.getString("openid");
            if(StringUtils.isBlank(openid)){
                // todo 如果为空，直接报错
                log.error("微信登录失败|openid为空");
                throw new GlobalException(ErrorCode.USER_NOT_FOUND);
            }

            // todo 要增加逻辑删除标识

            // 使用openId查询用户表，如果不存在，创建用户
            UserInfo currentUser = userInfoService.lambdaQuery()
                    .eq(UserInfo::getOpenId, openid)
                    .one();

            if(Objects.isNull(currentUser)){
                currentUser = new UserInfo()
                        .setOpenId(openid);

                userInfoService.save(currentUser);
            }

            // todo id要用uuid
            // 生成token并返回
            String token = JwtUtils.generateToken(currentUser.getId().toString());
            log.info("用户登录成功，生成token: {}", token);

            String userId = JwtUtils.getUserIdFromToken(token);
            log.info("用户id: {}", userId);

            return token;

        } catch (IOException e) {
            log.error("请求微信接口失败", e);
        }
        return url;
    }
}
