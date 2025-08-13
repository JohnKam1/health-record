package com.Hootsybit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Hootsybit.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}