package com.Hootsybit.service.impl;

import com.Hootsybit.pojo.vo.UserInfoVO;
import com.Hootsybit.mapper.UserInfoMapper;
import com.Hootsybit.pojo.entity.UserInfo;
import com.Hootsybit.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public UserInfoVO getUserInfoVO(Long userId) {
        // 根据用户ID获取用户实体信息
        UserInfo userInfo = this.getById(userId);
        // 如果用户不存在，直接返回null
        if (userInfo == null) {
            return null;
        }

        // 创建用户信息视图对象
        UserInfoVO vo = new UserInfoVO();
        // 设置用户头像
        vo.setAvatar(userInfo.getAvatar());
        // 设置用户昵称
        vo.setNickname(userInfo.getNickname());
        // 判断用户信息是否完整（这里简单判断昵称和头像是否存在）
        boolean isComplete =  StringUtils.isNotBlank(userInfo.getNickname()) &&
                StringUtils.isNotBlank(userInfo.getAvatar());
        // 设置用户信息完整状态标记
        vo.setComplete(isComplete);

        // 返回组装好的用户信息视图对象
        return vo;
    }

    @Override
    public boolean updateAvatarAndNickname(Long userId, String avatar, String nickname) {
        // 创建用户信息对象
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setAvatar(avatar);
        userInfo.setNickname(nickname);
        
        // 更新用户信息
        return this.updateById(userInfo);
    }
}