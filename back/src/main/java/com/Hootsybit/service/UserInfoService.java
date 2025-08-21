package com.Hootsybit.service;

import com.Hootsybit.pojo.entity.UserInfo;
import com.Hootsybit.pojo.vo.UserInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserInfoService extends IService<UserInfo> {

    /**
     * 根据用户ID获取用户信息视图对象(UserInfoVO)
     * 该方法会查询用户基本信息，并判断用户信息是否完整
     *
     * @param userId 用户ID，用于查询指定用户的信息
     * @return UserInfoVO 包含用户基本信息和完整状态标记的视图对象，如果用户不存在则返回null
     */
    UserInfoVO getUserInfoVO(Long userId);

    /**
     * 更新用户头像和昵称
     *
     * @param userId 用户ID
     * @param avatar 头像URL
     * @param nickname 昵称
     * @return 是否更新成功
     */
    boolean updateAvatarAndNickname(Long userId, String avatar, String nickname);
}