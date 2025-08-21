package com.Hootsybit.config;

import com.Hootsybit.utils.JwtUtils;
// 修改引用的异常类
import com.Hootsybit.exception.GlobalException;
import com.Hootsybit.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    
    private static final ThreadLocal<String> USER_ID_HOLDER = new ThreadLocal<>();
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行微信登录接口
        if (request.getRequestURI().contains("/api/wechat/login")) {
            return true;
        }
        
        // 从header中获取token
        String token = request.getHeader("Authorization");
        
        // 如果token为空或无效，抛出异常
        if (token == null || token.isEmpty() || !JwtUtils.validateToken(token)) {
            log.error("Token无效或已过期");
            // 使用枚举类抛出异常
            throw new GlobalException(ErrorCode.TOKEN_INVALID);
        }
        
        // 解析token并将用户ID放入ThreadLocal
        String userId = JwtUtils.getUserIdFromToken(token);
        USER_ID_HOLDER.set(userId);
        
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理ThreadLocal中的数据
        USER_ID_HOLDER.remove();
    }
    
    /**
     * 获取当前线程中的用户ID
     * @return 用户ID
     */
    public static String getCurrentUserId() {
        return USER_ID_HOLDER.get();
    }
}